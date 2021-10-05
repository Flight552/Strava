package com.ybrflight552.fitnessapp.internet.network.interceptors

import com.ybrflight552.fitnessapp.utils.AuthInfo
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

class DeauthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBody = FormBody.Builder()
            .add("access_token", AuthInfo.ACCESS_TOKEN ?: "")
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