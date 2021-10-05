package com.ybrflight552.fitnessapp.di

import android.app.Application
import androidx.room.Room
import com.ybrflight552.fitnessapp.database.AthleteDao
import com.ybrflight552.fitnessapp.database.AthleteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DatabaseModule {
    @Provides
    fun providesDatabase(context: Application) : AthleteDatabase {
        return Room.databaseBuilder(
            context,
            AthleteDatabase::class.java,
            AthleteDatabase.DB_NAME)
            .build()
    }

    @Provides
    fun provideDao(db: AthleteDatabase) : AthleteDao {
        return db.athleteDao()
    }
}