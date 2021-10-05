package com.ybrflight552.fitnessapp.internet.data.remoteAthlete

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteBike(
    val id: String?,
    val primary: Boolean?,
    val name: String?,
    val resource_state: Int?,
    val distance: Int?
)