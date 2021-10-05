package com.ybrflight552.fitnessapp.viewModel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.repository.AuthRepository
import com.ybrflight552.fitnessapp.utils.lib.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

   // private val authRepository = AuthRepository(application)
    private val authService: AuthorizationService = AuthorizationService(getApplication())
    private val openAuthPageLiveEvent =
        SingleLiveEvent<Intent>()
    private val toastLiveEvent =
        SingleLiveEvent<Int>()
    private val loadingMutableLiveData = MutableLiveData(false)
    private val authSuccessLiveEvent =
        SingleLiveEvent<Unit>()
    private val liveData = MutableLiveData<Boolean>(false)

    val data: LiveData<Boolean>
        get() = liveData

    val openAuthPageLiveData: LiveData<Intent>
        get() = openAuthPageLiveEvent

    val authSuccessLiveData: LiveData<Unit>
        get() = authSuccessLiveEvent

    val toast: SingleLiveEvent<Int>
        get() = toastLiveEvent

    private val isSharedPrefMutableLiveData =
        SingleLiveEvent<Boolean>()
    val isShared: LiveData<Boolean>
        get() = isSharedPrefMutableLiveData

    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastLiveEvent.postValue(R.string.authorization_denied)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(true)
            authRepository.performTokenRequest(
                authService = authService,
                tokenRequest = tokenRequest,
                onComplete = {
                    loadingMutableLiveData.postValue(false)
                    authSuccessLiveEvent.postValue(Unit)
                },
                onError = {
                    loadingMutableLiveData.postValue(false)
                    toastLiveEvent.postValue(R.string.authorization_cancelled)
                }
            )
        }

    }

    // возвращаемое значение о проверке shared prefs
    fun getSharedPrefs() {
        viewModelScope.launch {
            isSharedPrefMutableLiveData.postValue(
                authRepository.checkValueFromSharedPrefs()
            )
        }
    }


    fun openLoginPage() {
        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest(),
        )
        openAuthPageLiveEvent.postValue(openAuthPageIntent)
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}