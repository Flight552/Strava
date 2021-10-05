package com.ybrflight552.fitnessapp.internet.data.remoteActivity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photos(
    val primary: String?,
    val count: Int?
)