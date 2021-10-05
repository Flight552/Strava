package com.ybrflight552.fitnessapp.internet.data.updateActivity

data class SegmentEffort(
    val achievements: List<String>?,
    val activity: Activity?,
    val athlete: Athlete?,
    val average_cadence: Double?,
    val average_watts: Double?,
    val device_watts: Boolean?,
    val distance: Double?,
    val elapsed_time: Int?,
    val end_index: Int?,
    val hidden: Boolean?,
    val id: Long?,
    val kom_rank: String?,
    val moving_time: Int?,
    val name: String?,
    val pr_rank: String?,
    val resource_state: Int?,
    val segment: Segment?,
    val start_date: String?,
    val start_date_local: String?,
    val start_index: Int?
)