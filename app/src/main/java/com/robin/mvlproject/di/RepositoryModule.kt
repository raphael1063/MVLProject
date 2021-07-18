package com.robin.mvlproject.di

import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.RepositoryImpl
import com.robin.mvlproject.data.api.AQIApiService
import com.robin.mvlproject.data.api.ApiService
import com.robin.mvlproject.data.api.LocationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        aqiApiService: AQIApiService,
        locationApiService: LocationApiService,
        apiService: ApiService
    ): Repository = RepositoryImpl(aqiApiService, locationApiService, apiService)
}