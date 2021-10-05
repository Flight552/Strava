package com.ybrflight552.fitnessapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ybrflight552.fitnessapp.database.models.ShoesContract

@Entity(tableName = ShoesContract.TABLE_SHOES)
class ShoesEntity(
    @PrimaryKey
    @ColumnInfo(name = ShoesContract.Columns.SHOES_ID)
    val id: String,
    @ColumnInfo(name = ShoesContract.Columns.PRIMARY)
    val primary: Boolean,
    @ColumnInfo(name = ShoesContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = ShoesContract.Columns.RESOURCE_STATE)
    val resource_state: Int,
    @ColumnInfo(name = ShoesContract.Columns.DISTANCE)
    val distance: Int
)