package com.ybrflight552.fitnessapp.internet.network

import android.net.Uri
import com.ybrflight552.fitnessapp.internet.network.interceptors.*
import com.ybrflight552.fitnessapp.utils.AuthInfo
import net.openid.appauth.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


object AppNetwork {

    // deauthorize request
    private val okHttpDeauth = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(DeauthInterceptor())
        .build()

    private val deauthRetrofit = Retrofit.Builder()
        .baseUrl(AuthInfo.DEAUTHORIZE_URI)
        .client(okHttpDeauth)
        .build()

    val deauthApi: AuthApi
        get() = deauthRetrofit.create()

    // authorized athlete request
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(AuthInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(AuthInfo.AUTH_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    val authApi: AuthApi
        get() = retrofit.create()

    //добавить вес атлета на сервер
    private val okHttpAddWeight = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addNetworkInterceptor(AddWeightInterceptor())
        .build()

    private val retroAdd = Retrofit.Builder()
        .baseUrl(AuthInfo.AUTH_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpAddWeight)
        .build()

    val addWeightApi: AuthApi
    get() = retroAdd.create()
    //------------------------------------- get activities request
    private val activityToken = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(ActivityInterceptor())
        .build()

    private val activityRetrofit = Retrofit.Builder()
        .baseUrl(AuthInfo.AUTH_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(activityToken)
        .build()

    val getActivityApi: AuthApi
        get() = activityRetrofit.create()

    //-------------------------------------------------create activity

    private val activityOkHttp = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(CreateInterceptor())
        .build()

    private val createRetrofit = Retrofit.Builder()
        .baseUrl(AuthInfo.AUTH_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(activityOkHttp)
        .build()
    val uploadActivity: AuthApi
        get() = createRetrofit.create()

    //----------------------------- refresh token request
    private
    val okHttpRefreshToken = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(TokenInterceptor())
        .build()

    private val retrofitRefreshToken = Retrofit.Builder()
        .baseUrl(AuthInfo.AUTH_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpRefreshToken)
        .build()

    val refreshApi: AuthApi
        get() = retrofitRefreshToken.create()
    //-----------------------------

    // initial authorization request
    fun authRequest(): AuthorizationRequest {
        val serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(AuthInfo.INTENT_URI),
            Uri.parse(AuthInfo.TOKEN_URL),
        )

        val redirectUri = Uri.parse(AuthInfo.CALLBACK_URL)

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthInfo.CLIENT_ID.toString(),
            AuthInfo.RESPONSE_CODE,
            redirectUri
        ).setAdditionalParameters(
            mapOf(
                "approval_prompt" to AuthInfo.APPROVAL_PROMPT
            )
        )
            .setScope(AuthInfo.SCOPE)
            .build()
    }

}