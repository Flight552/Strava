package com.ybrflight552.fitnessapp.di

import android.app.Application
import com.ybrflight552.fitnessapp.repository.AthleteRepository
import com.ybrflight552.fitnessapp.repository.AuthRepository
import com.ybrflight552.fitnessapp.viewModel.AthleteRequestsViewModel
import com.ybrflight552.fitnessapp.viewModel.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    fun provideAthleteViewModel(
        context: Application,
        athleteRepository: AthleteRepository
    ) : AthleteRequestsViewModel {
        return AthleteRequestsViewModel(
            app = context,
            athleteRepository = athleteRepository
        )
    }

    @Provides
    fun provideAuthViewModel(
        context: Application,
        authRepository: AuthRepository
    ): AuthViewModel {
        return AuthViewModel(
            application = context,
            authRepository = authRepository
        )
    }
}