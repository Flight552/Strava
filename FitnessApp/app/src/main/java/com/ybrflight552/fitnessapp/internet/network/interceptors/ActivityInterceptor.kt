package com.ybrflight552.fitnessapp.internet.network.interceptors

import com.ybrflight552.fitnessapp.utils.AuthInfo
import okhttp3.Interceptor
import okhttp3.Response

class ActivityInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${AuthInfo.ACCESS_TOKEN}")
            .build()
        val originalURL = originalRequest.url
        val response = originalRequest.newBuilder()
            .url(originalURL)
            .build()
        return chain.proceed(response)
    }
}