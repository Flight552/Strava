package com.ybrflight552.fitnessapp.internet.data.createActivity

import androidx.room.Entity
import com.ybrflight552.fitnessapp.database.models.ActivityContract

data class CreateActivityResponse(
    // The unique identifier of the upload
    val id: Int?,
    // The unique identifier of the upload in string format
    val id_str: String?,
    // The identifier of the activity this upload resulted into
    val activity_id: Int,
    // The error associated with this upload
    val error: String?,
    // The external identifier of the upload
    val external_id: String?,
    // The status of this upload
    val status: String?
)