package com.ybrflight552.fitnessapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ybrflight552.fitnessapp.database.models.ActivityContract

@Entity(tableName = ActivityContract.ACTIVITY_TABLE)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ActivityContract.Columns.external_id)
    val external_id: Int? = null,
    @ColumnInfo(name = ActivityContract.Columns.server_id)
    val id: Long?,
    @ColumnInfo(name = ActivityContract.Columns.name)
    val name: String?,
    @ColumnInfo(name = ActivityContract.Columns.data_type)
    val type: String?,
    @ColumnInfo(name = ActivityContract.Columns.date_create)
    val start_date_local: String?,
    @ColumnInfo(name = ActivityContract.Columns.description)
    val description: String?,
    @ColumnInfo(name = ActivityContract.Columns.trainer)
    val trainer: Int?,
    @ColumnInfo(name = ActivityContract.Columns.commute)
    val commute: Int?,
    @ColumnInfo(name = ActivityContract.Columns.distance)
    val distance: Float?,
    @ColumnInfo(name = ActivityContract.Columns.elapsed_time)
    val elapsed_time: Long?
)