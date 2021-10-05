package com.ybrflight552.fitnessapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.ybrflight552.fitnessapp.database.entities.ActivityEntity
import com.ybrflight552.fitnessapp.database.entities.AthleteEntity
import com.ybrflight552.fitnessapp.database.entities.LocalEntity
import com.ybrflight552.fitnessapp.database.models.TokenContract
import com.ybrflight552.fitnessapp.database.models.AthleteContract
import com.ybrflight552.fitnessapp.database.entities.TokenEntity
import com.ybrflight552.fitnessapp.database.models.ActivityContract
import com.ybrflight552.fitnessapp.database.models.LocalContract

@Dao
interface AthleteDao {
    @Query("SELECT * FROM ${TokenContract.TABLE_TOKEN}")
    suspend fun getTokensInfo(): TokenEntity

    @Insert(onConflict = REPLACE)
    suspend fun insertTokenInfo(tokenEntity: TokenEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertRemoteAthlete(athlete: AthleteEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertSummaryActivity(activity: ActivityEntity)

    @Query("SELECT * FROM ${ActivityContract.ACTIVITY_TABLE} WHERE ${ActivityContract.Columns.external_id} = :id")
    suspend fun getActivityByExternalId(id: Long): ActivityEntity

    @Query("SELECT * FROM ${ActivityContract.ACTIVITY_TABLE}")
    suspend fun getActivitiesFromDb(): List<ActivityEntity>

    @Query("SELECT * FROM ${AthleteContract.TABLE_ATHLETE}")
    suspend fun getAthleteInfo(): AthleteEntity

    @Query("SELECT * FROM ${LocalContract.LOCAL_TABLE} ")
    suspend fun getOfflineActivities(): List<LocalEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertLocalActivity(activity: LocalEntity)

    @Query("DELETE FROM ${LocalContract.LOCAL_TABLE}")
    suspend fun deleteFromLocalDB()

    @Query("DELETE FROM ${ActivityContract.ACTIVITY_TABLE}")
    suspend fun deleteActivityDB()

    @Query("DELETE FROM ${TokenContract.TABLE_TOKEN}")
    suspend fun deleteTokenDB()

    @Query("SELECT ${"id"} FROM ${ActivityContract.ACTIVITY_TABLE} ORDER BY ${"external_id"} DESC LIMIT 1")
    suspend fun getTheLastRowServerDB() : Long

    @Query("SELECT ${"id"} FROM ${LocalContract.LOCAL_TABLE} ORDER BY ${"external_id"} DESC LIMIT 1")
    suspend fun getTheLastRowLDB() : Long?

    @Query("UPDATE ${AthleteContract.TABLE_ATHLETE} SET ${AthleteContract.Columns.WEIGHT} = :weight")
    suspend fun updateWeightDB(weight: Float)

}