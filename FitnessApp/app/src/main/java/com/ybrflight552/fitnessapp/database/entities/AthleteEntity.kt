package com.ybrflight552.fitnessapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ybrflight552.fitnessapp.database.models.AthleteContract

@Entity(tableName = AthleteContract.TABLE_ATHLETE)
data class AthleteEntity(
    @PrimaryKey
    @ColumnInfo(name = AthleteContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = AthleteContract.Columns.USERNAME)
    val username: String?,
    @ColumnInfo(name = AthleteContract.Columns.RESOURCE_STATE)
    val resource_state: Int?,
    @ColumnInfo(name = AthleteContract.Columns.FIRSTNAME)
    val firstname: String?,
    @ColumnInfo(name = AthleteContract.Columns.LASTNAME)
    val lastname: String?,
    @ColumnInfo(name = AthleteContract.Columns.BIO)
    val bio: String?,
    @ColumnInfo(name = AthleteContract.Columns.CITY)
    val city: String?,
    @ColumnInfo(name = AthleteContract.Columns.STATE)
    val state: String?,
    @ColumnInfo(name = AthleteContract.Columns.COUNTRY)
    val country: String?,
    @ColumnInfo(name = AthleteContract.Columns.SEX)
    val sex: String?,
    @ColumnInfo(name = AthleteContract.Columns.PREMIUM)
    val premium: Boolean? = false,
    @ColumnInfo(name = AthleteContract.Columns.SUMMIT)
    val summit: Boolean? = false,
    @ColumnInfo(name = AthleteContract.Columns.CREATED_AT)
    val created_at: String?,
    @ColumnInfo(name = AthleteContract.Columns.UPDATED_AT)
    val updated_at: String?,
    @ColumnInfo(name = AthleteContract.Columns.BADGE_TYPE_ID)
    val badge_type_id: Int?,
    @ColumnInfo(name = AthleteContract.Columns.WEIGHT)
    val weight: Float?,
    @ColumnInfo(name = AthleteContract.Columns.PROFILE_MEDIUM)
    val profile_medium: String?,
    @ColumnInfo(name = AthleteContract.Columns.PROFILE)
    val profile: String?,
    @ColumnInfo(name = AthleteContract.Columns.FRIEND)
    val friend: Int?,
    @ColumnInfo(name = AthleteContract.Columns.FOLLOWER)
    val follower: Int?,
    @ColumnInfo(name = AthleteContract.Columns.FOLLOWER_COUNT)
    val follower_count: Int?,
    @ColumnInfo(name = AthleteContract.Columns.FRIEND_COUNT)
    val friend_count: Int?,
    @ColumnInfo(name = AthleteContract.Columns.MUTUAL_FRIEND_COUNT)
    val mutual_friend_count: Int?,
    @ColumnInfo(name = AthleteContract.Columns.ATHLETE_TYPE)
    val athlete_type: Int?,
    @ColumnInfo(name = AthleteContract.Columns.DATE_PREFERENCE)
    val date_preference: String?,
    @ColumnInfo(name = AthleteContract.Columns.MEASUREMENT_PREFERENCE)
    val measurement_preference: String?,
    @ColumnInfo(name = AthleteContract.Columns.FTP)
    val ftp: String?
//    @ColumnInfo(name = AthleteContract.Columns.CLUBS)
//    val clubs: Int,
//    @ColumnInfo(name = AthleteContract.Columns.BIKES)
//    val bikes: Int,
//    @ColumnInfo(name = AthleteContract.Columns.SHOES)
//    val shoes: Int
)