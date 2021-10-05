package com.ybrflight552.fitnessapp.internet.data.remoteAthlete

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteAthlete(
    val id: Long?,
    val username: String?,
    val resource_state: Int?,
    val firstname: String?,
    val lastname: String?,
    val bio: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val sex: String?,
    val premium: Boolean?,
    val summit: Boolean?,
    val created_at: String?,
    val updated_at: String?,
    val badge_type_id: Int?,
    val weight: Float?,
    val profile_medium: String?,
    val profile: String?,
    val friend: Int?,
    val follower: Int?,
    val follower_count: Int?,
    val friend_count: Int?,
    val mutual_friend_count: Int?,
    val athlete_type: Int?,
    val date_preference: String?,
    val measurement_preference: String?,
    val ftp: String?,
    val clubs: List<RemoteClub>?,
    val bikes: List<RemoteBike>?,
    val shoes: List<RemoteShoes>?
    )