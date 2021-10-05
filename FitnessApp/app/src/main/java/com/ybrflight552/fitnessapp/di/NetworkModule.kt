package com.ybrflight552.fitnessapp.di

import com.ybrflight552.fitnessapp.internet.network.AppNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {
    @Provides
    fun providesApi() : AppNetwork {
        return AppNetwork
    }
}