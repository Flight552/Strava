package com.ybrflight552.fitnessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ybrflight552.fitnessapp.database.entities.*

@Database(
    entities = [
        TokenEntity::class,
        AthleteEntity::class,
        BikeEntity::class,
        ShoesEntity::class,
        ActivityEntity::class,
        LocalEntity::class
       // ClubEntity::class
    ], version = AthleteDatabase.DATABASE_VERSION
)
abstract class AthleteDatabase : RoomDatabase() {

    abstract fun athleteDao(): AthleteDao

    companion object {
        const val DB_NAME = "athlete_db"
        const val DATABASE_VERSION = 1
    }
}