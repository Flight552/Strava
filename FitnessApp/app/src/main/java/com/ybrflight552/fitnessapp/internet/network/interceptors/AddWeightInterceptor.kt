package com.ybrflight552.fitnessapp.internet.network.interceptors

import com.ybrflight552.fitnessapp.utils.AuthInfo
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddWeightInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val body = FormBody.Builder()
            .build()
        val originalRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${AuthInfo.ACCESS_TOKEN}")
            .build()
        val originalUrl = originalRequest.url
        val response: Request = originalRequest.newBuilder()
            .put(body)
            .url(originalUrl)
            .build()
        return chain.proceed(response)
    }
}