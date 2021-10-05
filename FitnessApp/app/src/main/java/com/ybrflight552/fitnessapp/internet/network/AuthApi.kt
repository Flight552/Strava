package com.ybrflight552.fitnessapp.internet.network

import retrofit2.http.*
import androidx.annotation.IntRange
import com.ybrflight552.fitnessapp.internet.data.RemoteToken
import com.ybrflight552.fitnessapp.internet.data.remoteActivity.DetailedActivity
import com.ybrflight552.fitnessapp.internet.data.remoteActivity.ServerActivity
import com.ybrflight552.fitnessapp.internet.data.remoteAthlete.RemoteAthlete


interface AuthApi {
    @GET("athlete")
    suspend fun getAuthenticatedUser(
    ): RemoteAthlete

    @GET("athlete/activities ")
    suspend fun getAthleteActivity(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("per_page") @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): List<ServerActivity>

    @POST("oauth/token")
    suspend fun postRefreshToken(): RemoteToken

    @POST("activities")
    suspend fun createActivity(
        @Query("name")
        name: String,
        @Query("type")
        type: String,
        @Query("start_date_local")
        start_date_local: String,
        @Query("elapsed_time")
        elapsed_time: Long,
        @Query("description")
        description: String,
        @Query("distance")
        distance: Float,
        @Query("trainer")
        trainer: Int,
        @Query("commute")
        commute: Int,
    ) : DetailedActivity

    @POST("oauth/deauthorize")
    suspend fun deauthorization()

    @PUT("athlete")
    suspend fun addWeightToServer(
        @Query("weight")
        weight: Float
    ) : RemoteAthlete

    companion object {

        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 30
    }
}