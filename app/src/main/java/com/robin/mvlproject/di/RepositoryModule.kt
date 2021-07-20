package com.robin.mvlproject.di

import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.RepositoryImpl
import com.robin.mvlproject.data.api.AQIApiService
import com.robin.mvlproject.data.api.ApiService
import com.robin.mvlproject.data.api.LocationApiService
import com.robin.mvlproject.data.dao.BookDao
import com.robin.mvlproject.data.dao.LabelDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
     abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository
}