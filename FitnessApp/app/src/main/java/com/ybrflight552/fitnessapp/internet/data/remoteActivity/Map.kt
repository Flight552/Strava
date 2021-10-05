package com.ybrflight552.fitnessapp.internet.data.remoteActivity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Map(
    val id : String?,
    val summary_polyline : String?,
    val resource_state : Int?
)
