package com.ybrflight552.fitnessapp.internet.network.interceptors

import com.ybrflight552.fitnessapp.utils.AuthInfo
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

class CreateInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val body = FormBody.Builder()
            .build()
        val originalRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${AuthInfo.ACCESS_TOKEN}")
            .build()
        val originalURL = originalRequest.url
        val response = originalRequest.newBuilder()
            .post(body)
            .url(originalURL)
            .build()
        return chain.proceed(response)
    }

    // "https://www.strava.com/api/v3/activities" name='value' type='value' start_date_local='value' elapsed_time='value' description='value' distance='value' trainer='value' commute='value' "Authorization: Bearer [[token]]"
}