package com.ybrflight552.fitnessapp.internet.data.remoteAthlete

data class RemoteLoginAthlete(
    val id: Long?,
    val username: String? = null,
    val resource_state: Int? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val bio: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val sex: String? = null,
    val premium: Boolean? = null,
    val summit: Boolean? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val badge_type_id: Int? = null,
    val weight: Float? = null,
    val profile_medium: String? = null,
    val profile: String? = null,
    val friend: Int? = null,
    val follower: Int? = null
)
