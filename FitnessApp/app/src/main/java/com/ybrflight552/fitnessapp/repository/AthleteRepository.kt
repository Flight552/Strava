package com.ybrflight552.fitnessapp.repository

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.database.AthleteDao
import com.ybrflight552.fitnessapp.database.entities.ActivityEntity
import com.ybrflight552.fitnessapp.database.entities.AthleteEntity
import com.ybrflight552.fitnessapp.database.entities.LocalEntity
import com.ybrflight552.fitnessapp.database.entities.TokenEntity
import com.ybrflight552.fitnessapp.internet.data.RemoteToken
import com.ybrflight552.fitnessapp.internet.data.remoteActivity.ServerActivity
import com.ybrflight552.fitnessapp.internet.data.remoteAthlete.RemoteAthlete
import com.ybrflight552.fitnessapp.internet.network.AppNetwork
import com.ybrflight552.fitnessapp.utils.AuthInfo
import java.io.IOException
import javax.inject.Inject

class AthleteRepository @Inject constructor(
    private val context: Context,
    private val api: AppNetwork,
    private val db: AthleteDao,
) {
    //-------------------------------------------------------------------------- модуль приложения

    // деавторизация приложения
    suspend fun deauthorizeAthlete() {
        api.deauthApi.deauthorization()
    }
    //-------------------------------------------------------------------------- модуль токена

    // получение обновленного токена
    private suspend fun refreshApi(): RemoteToken {
        return api.refreshApi.postRefreshToken()
    }

    // Проверка если время до истечения срока действия токена меньше часа
    // если да, то получить новый токен и занести его в датабазу
    private suspend fun getTokensFromDB() {
        val tokens: TokenEntity? = db.getTokensInfo()
        val id = tokens?.athleteId
        val expires = tokens?.expires_at
        if (expires != null && id != null) {
            val expirationTime = (expires / 1000) - (System.currentTimeMillis() / 1000)
            if (expirationTime < 3600L) {
                val remoteToken = refreshApi()
                AuthInfo.ACCESS_TOKEN = remoteToken.access_token.orEmpty()
                AuthInfo.REFRESH_TOKEN = remoteToken.refresh_token.orEmpty()
                db.insertTokenInfo(
                    TokenEntity(
                        id,
                        remoteToken.expires_at,
                        remoteToken.refresh_token,
                        remoteToken.access_token,
                        remoteToken.scope
                    )
                )
                //     если нет, то присвоить токенам значения из датабазы
            } else {
                AuthInfo.ACCESS_TOKEN = tokens.access_token.orEmpty()
                AuthInfo.REFRESH_TOKEN = tokens.refresh_token.orEmpty()
            }
        }
    }

    //-------------------------------------------------------------------------- модуль атлета
    // получить информацию об атлете для установки в профиль
    suspend fun getAthleteResponse(): AthleteEntity {
        getTokensFromDB()
        return try {
            val athlete = api.authApi.getAuthenticatedUser()
            // записать атлета в датабазу если есть доступ к серверу
            val athleteEntity = athleteConverter(athlete)
            saveAthleteToDB(athleteEntity)
            athleteEntity
        } catch (e: Exception) {
            //  если не получается достать атлета с сервера - добавить из старой датабазы
            db.getAthleteInfo()
        }
    }

    // добавить вес атлета на сервер
    suspend fun addWeight(weight: Float, onResponse: (Int) -> Unit) {
        try {
            // записываем вес в дб
            db.updateWeightDB(weight)
            saveWeightToSharedPrefs(weight)
            Log.d("AthleteRepository", "save to db ${db.getAthleteInfo().weight}")
            try {
                // записываем вес на сервер
                AppNetwork.addWeightApi.addWeightToServer(weight)
                onResponse(AuthInfo.RESPONSE_SUCCESS)
            } catch (e: Exception) {
                onResponse(AuthInfo.RESPONSE_ERROR_INTERNET)
                Log.d("AthleteRepository", "add weight to server error $e")
            }

        } catch (e: Exception) {
            onResponse(AuthInfo.RESPONSE_ERROR_DATABASE)
            Log.d("AthleteRepository", "Add weight to db error $e")
        }
    }

    // записываем в shared prefs вес атлета в оффлайне
    private fun saveWeightToSharedPrefs(weight: Float) {
        try {
            context.getSharedPreferences(
                AuthInfo.SHARED_TOKEN_NAME,
                AppCompatActivity.MODE_PRIVATE
            ).apply {
                edit().putFloat(AuthInfo.SHARED_ATHLETE_WEIGHT, weight).apply()
            }
        } catch (e: Exception) {
            Log.d("AuthRepository", "Put shared prefs error $e")
        }
    }

    // записываем в shared prefs вес атлета в оффлайне
    private fun saveTimeLastActivityToSharedPrefs(time: Long) {
        try {
            context.getSharedPreferences(
                AuthInfo.SHARED_TOKEN_NAME,
                AppCompatActivity.MODE_PRIVATE
            ).apply {
                edit().putLong(AuthInfo.SHARED_ACTIVITY_TIME, time).apply()
            }
        } catch (e: Exception) {
            Log.d("AuthRepository", "Put shared prefs error $e")
        }
    }

    private fun getSharedWeight(): Float {
        var weight: Float? = null
        try {
            context.getSharedPreferences(AuthInfo.SHARED_TOKEN_NAME, AppCompatActivity.MODE_PRIVATE)
                .apply {
                    weight = getFloat(AuthInfo.SHARED_ATHLETE_WEIGHT, 0F)
                }
        } catch (e: Exception) {
            Log.d("AuthRepository", "Get shared prefs error $e")
            weight = 0F
        }
        return weight!!
    }

    //конвертировать Атлета с сервера в датабазу
    private fun athleteConverter(athlete: RemoteAthlete): AthleteEntity {
        return AthleteEntity(
            id = athlete.id!!,
            username = athlete.username,
            resource_state = athlete.resource_state,
            firstname = athlete.firstname,
            lastname = athlete.lastname,
            bio = athlete.bio,
            city = athlete.city,
            state = athlete.state,
            country = athlete.country,
            sex = athlete.sex,
            premium = athlete.premium,
            summit = athlete.summit,
            created_at = athlete.created_at,
            updated_at = athlete.updated_at,
            badge_type_id = athlete.badge_type_id,
            weight = athlete.weight,
            profile_medium = athlete.profile_medium,
            profile = athlete.profile,
            friend = athlete.friend,
            follower = athlete.follower,
            follower_count = athlete.follower_count,
            friend_count = athlete.friend_count,
            mutual_friend_count = athlete.mutual_friend_count,
            athlete_type = athlete.athlete_type,
            date_preference = athlete.date_preference,
            measurement_preference = athlete.measurement_preference,
            ftp = athlete.ftp
        )
    }

    // запись инфо об атлете в датабазу
    private suspend fun saveAthleteToDB(user: AthleteEntity) {
        try {
            db.insertRemoteAthlete(user)
        } catch (e: IOException) {
            Log.d("AthleteRepository", "save athlete to db error")
        }
    }
    //-------------------------------------------------------------------------- модуль активностей


    //--------------------получить список активностей атлета
    suspend fun getActivity(list: (List<ServerActivity>) -> Unit) {
        try { // с сервера
            val newList = api.getActivityApi.getAthleteActivity()
            list(newList)

        } catch (e: Exception) {
            // если есть ошибка доступа к серверу - достать список из датабазы
            list(convertActivities())
            Log.d("AthleteRepository", "athlete activity error $e")
        }
    }

    // создать активность
    suspend fun createNewActivity(
        name: String?,
        type: String?,
        date: String?,
        time: Long,
        description: String?,
        distance: String?,
        elapsed_time: String?,
        trainer: Int = 1,
        commute: Int = 1
    ): String {
        // проверка заполнености полей в activity и вывод ошибки
        return when {
            name.isNullOrEmpty() -> {
                AuthInfo.CREATE_NAME
            }
            type.isNullOrEmpty() || type == context.getString(R.string.choose_activity) -> {
                AuthInfo.CREATE_TYPE
            }
            date.isNullOrEmpty() -> {
                AuthInfo.CREATE_DATE
            }
            description.isNullOrEmpty() -> {
                AuthInfo.CREATE_DESCRIPTION
            }
            distance.isNullOrEmpty() -> {
                AuthInfo.CREATE_DISTANCE
            }
            elapsed_time.isNullOrEmpty() -> {
                AuthInfo.CREATE_DURATION
            }
            else -> {
                Log.d("AthleteRepository", "Elapsed time - $elapsed_time")
                //полная дата для сервера и дб
                val durationInSeconds = (time.toInt() * 60).toLong()
                val elapsedTimeInSeconds = elapsed_time.toLong() * 60
               // Log.d("AthleteRepository", "durationInSeconds - $durationInSeconds")
                var onServer: Boolean = false
                // добавить активность на сервер
                try {
                    onServer = uploadActivityToServer(
                        name, type, date, description, elapsedTimeInSeconds, distance.toFloat(),
                        trainer,
                        commute
                    )
                } catch (e: Exception) {
                    Log.d("AthleteRepository", "Network error $e")
                }

                try {
                    saveTimeLastActivityToSharedPrefs(time)
                } catch (e: Exception) {
                    Log.d("AthleteRepository", "Save time to shared error $e")
                }
                //добавить активность в дата базу если предыдущий блок выполнился,
                // значит база залилась на сервер
                try {
                    if (onServer) {
                        uploadActivityToDb(
                            name,
                            type,
                            date,
                            time,
                            description,
                            elapsedTimeInSeconds,
                            distance.toFloat(),
                            trainer,
                            commute
                        )
                    } else {
                        // если нет сети, то добавить запись в локальную базу, пока не появится сеть
                        uploadActivityToLocalDb(
                            name,
                            type,
                            date,
                            time,
                            description,
                            elapsedTimeInSeconds,
                            distance.toFloat(),
                            trainer,
                            commute
                        )
                    }
                } catch (e: Exception) {
                    Log.d("AthleteRepository", "Database error: $e")
                }
                "finish"
            }
        }
    }

    // добавить активность на сервер
    private suspend fun uploadActivityToServer(
        name: String,
        type: String,
        date: String,
        description: String,
        elapsed_time: Long,
        distance: Float,
        trainer: Int,
        commute: Int
    ): Boolean {
        return try {
            api.uploadActivity.createActivity(
                name,
                type,
                date,
                elapsed_time,
                description,
                distance,
                trainer,
                commute
            )
            true
        } catch (e: Exception) {
            Log.d("AthleteRepository", "Create activity exception $e")
            false
        }
    }

    //добавить активность в дата базу
    private suspend fun uploadActivityToDb(
        name: String,
        type: String,
        date: String,
        time: Long,
        description: String,
        elapsed_time: Long,
        distance: Float,
        trainer: Int,
        commute: Int,
    ) {
        try {
            db.insertSummaryActivity(
                ActivityEntity(
                    id = time,
                    name = name,
                    type = type,
                    start_date_local = date,
                    description = description,
                    trainer = trainer,
                    commute = commute,
                    distance = distance,
                    elapsed_time = elapsed_time
                )
            )
        } catch (e: Exception) {
            Log.d("AthleteRepository", "Database activity exception $e")

        }
    }

    //добавить активность в дата базу без отправки на сервер
    private suspend fun uploadActivityToLocalDb(
        name: String,
        type: String,
        date: String,
        time: Long,
        description: String,
        elapsed_time: Long,
        distance: Float,
        trainer: Int,
        commute: Int,
    ) {
        try {
            db.insertLocalActivity(
                LocalEntity(
                    id = time,
                    name = name,
                    type = type,
                    start_date_local = date,
                    description = description,
                    trainer = trainer,
                    commute = commute,
                    distance = distance,
                    elapsed_time = elapsed_time
                )
            )
        } catch (e: Exception) {
            Log.d("AthleteRepository", "Database local activity exception $e")

        }
    }


    // переделать список из датабазы ActivityEntity в список активности сервера ServerActivity
    private suspend fun convertActivities(): List<ServerActivity> {
        val dbList = db.getActivitiesFromDb()
        val localDbList = db.getOfflineActivities()
        val serverList: MutableList<ServerActivity> = mutableListOf<ServerActivity>()
        // сначала достаем из общей ДБ
        dbList.forEach { activityEntity ->
            serverList.add(
                ServerActivity(
                    external_id = activityEntity.external_id,
                    name = activityEntity.name,
                    type = activityEntity.type,
                    start_date_local = activityEntity.start_date_local,
                    distance = activityEntity.distance?.toFloat()
                )
            )
        }
        // Затем достаем из локальной ДБ LocalEntity, где лежат неотправленные на сервер записи
        if (localDbList.isNotEmpty()) {
            localDbList.forEach { activityEntity ->
                serverList.add(
                    ServerActivity(
                        external_id = activityEntity.external_id,
                        name = activityEntity.name,
                        type = activityEntity.type,
                        start_date_local = activityEntity.start_date_local,
                        distance = activityEntity.distance?.toFloat()
                    )
                )
            }
        }
        //сортируем по дате
        return serverList.sortedBy { it.start_date_local }.toList()
    }

    //запись активностей из датабазы на сервер при появлении сети
    suspend fun saveActivitiesToServer() {
        val list = db.getOfflineActivities()
        val weight = getSharedWeight()
        Log.d("AthleteRepository", "offline activities $list")
        try {
            AppNetwork.addWeightApi.addWeightToServer(weight)
        } catch (e: Exception) {
            Log.d("AthleteRepository", "send weight error $e")
        }
        if (list.isNotEmpty()) {
            try {
                list.forEach { activity ->
                    AppNetwork.uploadActivity.createActivity(
                        name = activity.name,
                        type = activity.type,
                        start_date_local = activity.start_date_local,
                        description = activity.description,
                        trainer = activity.trainer,
                        commute = activity.commute,
                        distance = activity.distance,
                        elapsed_time = activity.elapsed_time
                    )
                    db.insertSummaryActivity(
                        ActivityEntity(
                            id = 0L,
                            name = activity.name,
                            type = activity.type,
                            start_date_local = activity.start_date_local,
                            description = activity.description,
                            trainer = activity.trainer,
                            commute = activity.commute,
                            distance = activity.distance,
                            elapsed_time = activity.elapsed_time
                        )
                    )
                }
                // стираем из локальной датабазы отправленные данные
                db.deleteFromLocalDB()
            } catch (e: Exception) { }
        }
    }

    // удалить БД пользователя
    suspend fun deleteAllDB() {
        try {
            db.deleteFromLocalDB()
        } catch (e: Exception) {
            Log.d("AthleteRepository", "Unable to delete Local data base")
        }

        try {
            db.deleteActivityDB()
        } catch (e: Exception) {
            Log.d("AthleteRepository", "Unable to delete Activity data base")
        }
        try {
            db.deleteTokenDB()
        } catch (e: Exception) {
            Log.d("AthleteRepository", "Unable to delete Token data base")
        }
        try {
            context.getSharedPreferences(AuthInfo.SHARED_TOKEN_NAME, AppCompatActivity.MODE_PRIVATE)
                .apply {
                    edit().clear().apply()
                }
        } catch (e: Exception) {
            Log.d("AthleteRepository", "Unable to delete SHARED_TOKEN_NAME")
        }
        try {
            context.getSharedPreferences(
                AuthInfo.ONBOARDING_PREF_NAME,
                AppCompatActivity.MODE_PRIVATE
            ).apply {
                edit().clear().apply()
            }
        } catch (e: Exception) {
            Log.d("AthleteRepository", "Unable to delete ONBOARDING_PREF_NAME")
        }
    }

    // get language from shared pref
    fun getLanguageFromSharedPref(onResponse: (lang: String) -> Unit) {
        context.getSharedPreferences(AuthInfo.SHARED_TOKEN_NAME, AppCompatActivity.MODE_PRIVATE)
            .apply {
                try {
                    val ln = getString(AuthInfo.SHARED_LANGUAGE_KEY, "en")
                    if (!ln.isNullOrEmpty())
                        onResponse(ln)
                } catch (e: Exception) {
                    Log.d("AuthRepository", "shared pref error $e")
                }
            }
    }
}
