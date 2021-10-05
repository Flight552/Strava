package com.ybrflight552.fitnessapp.internet.network.interceptors

import com.ybrflight552.fitnessapp.utils.AuthInfo
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBody = FormBody.Builder()
            .add("client_id", "${AuthInfo.CLIENT_ID}")
            .add("client_secret", AuthInfo.CLIENT_SECRET)
            .add("grant_type", AuthInfo.TYPE)
            .add("refresh_token", AuthInfo.REFRESH_TOKEN)
            .build()
        val originalRequest = chain.request().newBuilder()
            .build()
        val originalURL = originalRequest.url
        val response = originalRequest.newBuilder()
            .post(requestBody)
            .url(originalURL)
            .build()
        return chain.proceed(response)
    }
}