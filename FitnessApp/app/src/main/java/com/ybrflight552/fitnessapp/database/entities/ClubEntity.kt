package com.ybrflight552.fitnessapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ybrflight552.fitnessapp.database.models.ClubContract

@Entity(tableName = ClubContract.TABLE_CLUB)
data class ClubEntity(
    @PrimaryKey
    @ColumnInfo(name = ClubContract.Columns.CLUB_ID)
    val id: Long,
    @ColumnInfo(name = ClubContract.Columns.RESOURCE_STATE)
    val resource_state: Int?,
    @ColumnInfo(name = ClubContract.Columns.NAME)
    val name: String?,
    @ColumnInfo(name = ClubContract.Columns.PROFILE_MEDIUM)
    val profile_medium: String?,
    @ColumnInfo(name = ClubContract.Columns.PROFILE)
    val profile: String?,
    @ColumnInfo(name = ClubContract.Columns.COVER_PHOTO)
    val cover_photo: String?,
    @ColumnInfo(name = ClubContract.Columns.COVER_PHOTO_SMALL)
    val cover_photo_small: String?,
    @ColumnInfo(name = ClubContract.Columns.SPORT_TYPE)
    val sport_type: String?,
    @ColumnInfo(name = ClubContract.Columns.CITY)
    val city: String?,
    @ColumnInfo(name = ClubContract.Columns.STATE)
    val state: String?,
    @ColumnInfo(name = ClubContract.Columns.COUNTRY)
    val country: String?,
    @ColumnInfo(name = ClubContract.Columns.PRIVATE)
    val private: Boolean?,
    @ColumnInfo(name = ClubContract.Columns.MEMBER_COUNT)
    val member_count: Int?,
    @ColumnInfo(name = ClubContract.Columns.FEATURED)
    val featured: Boolean?,
    @ColumnInfo(name = ClubContract.Columns.VERIFIED)
    val verified: Boolean?,
    @ColumnInfo(name = ClubContract.Columns.URL)
    val url: String?,
    @ColumnInfo(name = ClubContract.Columns.MEMBERSHIP)
    val membership: String?,
    @ColumnInfo(name = ClubContract.Columns.ADMIN)
    val admin: Boolean?,
    @ColumnInfo(name = ClubContract.Columns.OWNER)
    val owner: Boolean?,
    @ColumnInfo(name = ClubContract.Columns.DESCRIPTION)
    val description: String?,
    @ColumnInfo(name = ClubContract.Columns.CLUB_TYPE)
    val club_type: String?,
    @ColumnInfo(name = ClubContract.Columns.POST_COUNT)
    val post_count: Int?,
    @ColumnInfo(name = ClubContract.Columns.OWNER_ID)
    val owner_id: Int?,
    @ColumnInfo(name = ClubContract.Columns.FOLLOWING_COUNT)
    val following_count: Int?
)