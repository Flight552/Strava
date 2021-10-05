package com.ybrflight552.fitnessapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ybrflight552.fitnessapp.database.models.TokenContract

@Entity(tableName = TokenContract.TABLE_TOKEN)
data class TokenEntity(
    @PrimaryKey
    @ColumnInfo(name = TokenContract.Columns.ATHLETE_ID)
    val athleteId: Long,
    @ColumnInfo(name = TokenContract.Columns.EXPIRES_AT)
    val expires_at: Long?,
    @ColumnInfo(name = TokenContract.Columns.REFRESH_TOKEN)
    val refresh_token: String?,
    @ColumnInfo(name = TokenContract.Columns.ACCESS_TOKEN)
    val access_token: String?,
    @ColumnInfo(name = TokenContract.Columns.SCOPE)
    val scope: String?
)