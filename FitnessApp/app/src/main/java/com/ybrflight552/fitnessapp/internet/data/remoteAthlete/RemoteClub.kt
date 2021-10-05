package com.ybrflight552.fitnessapp.internet.data.remoteAthlete

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteClub(
    val id: Long? = null,
    val resource_state: Int? = null,
    val name: String? = null,
    val profile_medium: String? = null,
    val profile: String? = null,
    val cover_photo: String? = null,
    val cover_photo_small: String? = null,
    val sport_type: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val private: Boolean? = null,
    val member_count: Int? = null,
    val featured: Boolean? = null,
    val verified: Boolean? = null,
    val url: String? = null,
    val membership: String? = null,
    val admin: Boolean? = null,
    val owner: Boolean? = null,
    val description: String? = null,
    val club_type: String? = null,
    val post_count: Int? = null,
    val owner_id: Int? = null,
    val following_count: Int? = null
)