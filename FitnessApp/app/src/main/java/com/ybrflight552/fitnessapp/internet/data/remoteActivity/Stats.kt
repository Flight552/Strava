package com.ybrflight552.fitnessapp.internet.data.remoteActivity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stats(
    val type: String?,
    val visibility: String?
)