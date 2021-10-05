package com.ybrflight552.fitnessapp.internet.data.remoteActivity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResourceState(
    val resource_state: Int?,
    val metaAthlete: MetaAthlete?
)