package com.ybrflight552.fitnessapp.internet.data.updateActivity

data class Segment(
    val activity_type: String?,
    val average_grade: Double?,
    val city: String?,
    val climb_category: Int?,
    val country: String?,
    val distance: Double?,
    val elevation_high: Double?,
    val elevation_low: Double?,
    val end_latlng: List<LatLng>?,
    val hazardous: Boolean?,
    val id: Int?,
    val maximum_grade: Double?,
    val name: String?,
    val private: Boolean?,
    val resource_state: Int?,
    val starred: Boolean?,
    val start_latlng: List<LatLng>?,
    val state: String?
)