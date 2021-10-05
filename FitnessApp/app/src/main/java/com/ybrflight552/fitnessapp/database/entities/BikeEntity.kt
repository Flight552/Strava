package com.ybrflight552.fitnessapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ybrflight552.fitnessapp.database.models.BikeContract

@Entity(tableName = BikeContract.TABLE_BIKE)
data class BikeEntity(
    @PrimaryKey
    @ColumnInfo(name = BikeContract.Columns.BIKE_ID)
    val id: String,
    @ColumnInfo(name = BikeContract.Columns.PRIMARY)
    val primary: Boolean,
    @ColumnInfo(name = BikeContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = BikeContract.Columns.RESOURCE_STATE)
    val resource_state: Int,
    @ColumnInfo(name = BikeContract.Columns.DISTANCE)
    val distance: Int
)