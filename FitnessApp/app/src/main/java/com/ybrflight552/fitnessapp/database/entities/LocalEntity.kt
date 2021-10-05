package com.ybrflight552.fitnessapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ybrflight552.fitnessapp.database.models.LocalContract

@Entity(tableName = LocalContract.LOCAL_TABLE)
data class LocalEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LocalContract.Columns.external_id)
    val external_id: Int? = null,
    @ColumnInfo(name = LocalContract.Columns.server_id)
    val id: Long?,
    @ColumnInfo(name = LocalContract.Columns.name)
    val name: String,
    @ColumnInfo(name = LocalContract.Columns.data_type)
    val type: String,
    @ColumnInfo(name = LocalContract.Columns.date_create)
    val start_date_local: String,
    @ColumnInfo(name = LocalContract.Columns.description)
    val description: String,
    @ColumnInfo(name = LocalContract.Columns.trainer)
    val trainer: Int,
    @ColumnInfo(name = LocalContract.Columns.commute)
    val commute: Int,
    @ColumnInfo(name = LocalContract.Columns.distance)
    val distance: Float,
    @ColumnInfo(name = LocalContract.Columns.elapsed_time)
    val elapsed_time: Long
) {
}