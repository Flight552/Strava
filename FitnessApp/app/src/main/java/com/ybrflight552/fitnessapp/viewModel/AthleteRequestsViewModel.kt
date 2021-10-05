package com.ybrflight552.fitnessapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ybrflight552.fitnessapp.database.entities.AthleteEntity
import com.ybrflight552.fitnessapp.internet.data.remoteActivity.ServerActivity
import com.ybrflight552.fitnessapp.repository.AthleteRepository
import com.ybrflight552.fitnessapp.utils.lib.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AthleteRequestsViewModel @Inject constructor(
    app: Application,
    private val athleteRepository: AthleteRepository
) : AndroidViewModel(app) {

    private val remoteAthleteMutableData = MutableLiveData<AthleteEntity>()
    val remoteAthlete: LiveData<AthleteEntity>
        get() = remoteAthleteMutableData

    private val onSuccessMutableLiveData = MutableLiveData<Boolean>(false)
    val onSuccess: LiveData<Boolean>
        get() = onSuccessMutableLiveData

    private val onCheckResponse =
        SingleLiveEvent<String>()
    val onResponse: LiveData<String>
        get() = onCheckResponse

    private val onCheckError =
        SingleLiveEvent<Int>()
    val onError: LiveData<Int>
        get() = onCheckError

    private val listActivitiesMutableLiveData = MutableLiveData<List<ServerActivity>>()
    val listActivities: LiveData<List<ServerActivity>>
        get() = listActivitiesMutableLiveData

    //получения детальной информации об атлете
    fun athleteProfile() {
        viewModelScope.launch {
            try {
                val athlete = athleteRepository.getAthleteResponse()
                remoteAthleteMutableData.postValue(athlete)
            } catch (e: IOException) {
                Log.d("AthleteRequests", "Getting athlete error $e")
            }
        }
    }

    // деавторизиция
    fun deauthorizeRequest() {
        viewModelScope.launch {
            try {
                athleteRepository.deauthorizeAthlete()
                onSuccessMutableLiveData.postValue(true)
            } catch (e: Exception) {
                Log.d("AthleteRequests", "deathorization failure $e")
                onSuccessMutableLiveData.postValue(false)
            }
        }
    }

    // получение списка активностей
    fun getAthleteActivity() {
        viewModelScope.launch {
            try {
                onSuccessMutableLiveData.postValue(true)
                athleteRepository.getActivity() { list ->
                    listActivitiesMutableLiveData.postValue(list)
                }
                onSuccessMutableLiveData.postValue(false)
                Log.d("AthleteRequests", "request activity")
            } catch (e: Exception) {
                listActivitiesMutableLiveData.postValue(emptyList())
                onSuccessMutableLiveData.postValue(false)
                Log.d("AthleteRequests", "activity failure $e")
            }
        }
    }

    //создние активности атлета, проверка на заполнение полей
    fun createAthleteActivity(
        name: String?,
        type: String?,
        date: String?,
        time: Long,
        description: String?,
        distance: String?,
        duration: String?
    ) {
        try {
            onSuccessMutableLiveData.postValue(true)
            Log.d("AthleteRequests", "start adding to db")
            viewModelScope.launch {
                val response =
                    athleteRepository.createNewActivity(
                        name,
                        type,
                        date,
                        time,
                        description,
                        distance,
                        duration
                    )
                onCheckResponse.postValue(response)
                Log.d("AthleteRequests", "response $response")
                onSuccessMutableLiveData.postValue(false)
            }
        } catch (e: Exception) {
            Log.d("AthleteRequests", "activity failure $e")
            onSuccessMutableLiveData.postValue(false)
        }

    }

    //обновление активностей при появлении интернета
    fun updateLocalDB() {
        viewModelScope.launch {
            try {
                athleteRepository.saveActivitiesToServer()
            } catch (e: Exception) {
                Log.d("AthleteRequests" , "Save error ${e}")
            }
        }
    }

    // удалить все БД
    fun deleteAll() {
        viewModelScope.launch {
            try {
                athleteRepository.deleteAllDB()
            } catch (e: Exception) {
                onSuccessMutableLiveData.postValue(false)
            }
        }
    }

    // добавить вес атлета на сервер
    private fun addAthleteWeight(weight: Float) {
        viewModelScope.launch {
            try {
                onSuccessMutableLiveData.postValue(false)
                athleteRepository.addWeight(weight) {
                    onCheckError.postValue(it)
                }
                onSuccessMutableLiveData.postValue(true)
            } catch (e: Exception) {
                onSuccessMutableLiveData.postValue(true)
                Log.d("AthleteRequests", "Get weight error $e")
            }
        }
    }


    fun getLanguage() {
        viewModelScope.launch {
            athleteRepository.getLanguageFromSharedPref {
                onCheckResponse.postValue(it)
            }
        }
    }

    fun submitFLowWeight(weight: CharSequence) {
        Log.d("ProfileFragment", "Outside view model - $weight")
        viewModelScope.launch {
            flow {
                Log.d("ProfileFragment", "Inside view model - $weight")
                emit(weight)
            }.debounce(2000)
                .onStart { emit("") }
                .map {
                    try {
                        it.toString().toFloat()
                    } catch (e: NumberFormatException) {
                        0.0F
                    }
                }
                .collect { value ->
                    if (value > 0) {
                        addAthleteWeight(value)
                        Log.d("ProfileFragment", "Weight added")
                    }
                }
        }

    }

    override fun onCleared() {
        super.onCleared()
    }
}