package com.ybrflight552.fitnessapp.internet.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteToken(
    val token_type: String?,
    val access_token: String?,
    val expires_at: Long?,
    val expires_in: Long?,
    val refresh_token: String?,
    val scope: String?
)