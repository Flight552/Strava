package com.ybrflight552.fitnessapp.internet.data.remoteActivity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetaAthlete(
    val id : Long?,
    val resource_state: Int?
)