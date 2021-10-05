package com.ybrflight552.fitnessapp.di

import android.app.Application
import com.ybrflight552.fitnessapp.database.AthleteDao
import com.ybrflight552.fitnessapp.internet.network.AppNetwork
import com.ybrflight552.fitnessapp.repository.AthleteRepository
import com.ybrflight552.fitnessapp.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideAthleteRepository(
        context: Application,
        api: AppNetwork,
        database: AthleteDao,
    ) : AthleteRepository {
        return AthleteRepository(
            context = context,
            api = api,
            db = database
        )
    }

    @Provides
    fun provideAuthRepository(
        context: Application,
        database: AthleteDao
    ) : AuthRepository {
        return AuthRepository(
            context,
            database
        )
    }
}