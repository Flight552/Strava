package com.ybrflight552.fitnessapp.repository

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ybrflight552.fitnessapp.database.AthleteDao
import com.ybrflight552.fitnessapp.database.entities.TokenEntity
import com.ybrflight552.fitnessapp.internet.data.remoteAthlete.RemoteLoginAthlete
import com.ybrflight552.fitnessapp.internet.network.AppNetwork
import com.ybrflight552.fitnessapp.utils.AuthInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.openid.appauth.*
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val context: Context,
    private val db: AthleteDao
) {

    private val sharedPref =
        context.getSharedPreferences(AuthInfo.SHARED_TOKEN_NAME, AppCompatActivity.MODE_PRIVATE)

    fun getAuthRequest(): AuthorizationRequest {
        return AppNetwork.authRequest()
    }

    suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        onComplete: () -> Unit,
        onError: () -> Unit
    ) {
        authService.performTokenRequest(tokenRequest, getClientAuthentication()) { response, _ ->
            when {
                response != null -> {
                    val accessToken = response.accessToken.orEmpty()
                    val refreshToken = response.refreshToken.orEmpty()
                    val expirationTime = response.accessTokenExpirationTime
                    val scope = response.scope
                    Log.d("AuthRepository", "response ${response.scope}")
                    AuthInfo.REFRESH_TOKEN = refreshToken
                    AuthInfo.ACCESS_TOKEN = accessToken
                    //получение ID пользователя после запроса
                    val jsonRemoteAthlete = response.additionalParameters["athlete"].orEmpty()
                    //  val remoteLoginAthlete: RemoteLoginAthlete = jsonParsing(jsonRemoteAthlete)
                    val athleteID: Long? = jsonParsing(jsonRemoteAthlete).id
                    // запись в датабазу на отдельном потоке
                    runBlocking {
                        saveTokensToDatabase(
                            accessToken,
                            refreshToken,
                            expirationTime,
                            athleteID,
                            scope
                        )
                        sharedPref.edit()
                            .putLong(AuthInfo.SHARED_EXPIRATION_TIME_KEY, expirationTime!!)
                            .apply()
                        sharedPref.edit().putString(AuthInfo.SHARED_TOKEN_KEY, accessToken)
                            .apply()
                    }
                    onComplete()
                }
                else -> {
                    Log.d("AuthRepository", "response error")
                    onError()
                }
            }
        }
    }

    // Запись токенов в ДБ
    private suspend fun saveTokensToDatabase(
        accessToken: String,
        refreshToken: String,
        expiration: Long?,
        id: Long?,
        scope: String?
    ) {
        if (id != null) {
            val tokenEntity = TokenEntity(
                athleteId = id,
                expires_at = expiration,
                refresh_token = refreshToken,
                access_token = accessToken,
                scope = scope
            )
            withContext(Dispatchers.IO) {
                db.insertTokenInfo(tokenEntity)
            }
        }
    }


    // проверяем есть ли в shared prefs записи о последнем логине, если есть, то какие
    fun checkValueFromSharedPrefs(): Boolean {
        var isTrue = false
        try {
            context.getSharedPreferences(
                AuthInfo.SHARED_TOKEN_NAME,
                AppCompatActivity.MODE_PRIVATE
            ).apply {
                val expires = getLong(AuthInfo.SHARED_EXPIRATION_TIME_KEY, 0L)
                val expirationTime = (expires / 1000) - (System.currentTimeMillis() / 1000)
                if (expirationTime > 3600) {
                    isTrue = true
                }
            }
        } catch (e: Exception) {
            Log.d("AuthRepository", "Get shared prefs error $e")
            isTrue = false
        }

        return isTrue
    }

    private fun jsonParsing(remoteAthlete: String): RemoteLoginAthlete {
        return try {
            val jsonObject = JSONObject(remoteAthlete)
            val id = jsonObject.getLong("id")
            val firstname = jsonObject.getString("firstname")
            val lastname = jsonObject.getString("lastname")
            val city = jsonObject.getString("city")
            val country = jsonObject.getString("country")
            val profilePhotoMedium = jsonObject.getString("profile_medium")
            val sex = jsonObject.getString("sex")
            val weight = jsonObject.getDouble("weight")
            RemoteLoginAthlete(
                id = id,
                firstname = firstname,
                lastname = lastname,
                city = city,
                country = country,
                sex = sex,
                profile_medium = profilePhotoMedium,
                weight = weight.toFloat()
            )
        } catch (e: JSONException) {
            Log.d("AuthRepository", "json parsing error")
            RemoteLoginAthlete(id = null)
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(AuthInfo.CLIENT_SECRET)
    }
}