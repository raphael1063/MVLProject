package com.robin.mvlproject.di

import android.content.Context
import androidx.room.Room
import com.robin.mvlproject.base.ROOM_DB
import com.robin.mvlproject.data.AppDatabase
import com.robin.mvlproject.data.dao.BookDao
import com.robin.mvlproject.data.dao.LabelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDBModule {

    @Provides
    @Singleton
    fun provideLabelDao(appDatabase: AppDatabase): LabelDao =
        appDatabase.labelDao()

    @Provides
    @Singleton
    fun provideBookDao(appDatabase: AppDatabase): BookDao =
        appDatabase.bookDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, ROOM_DB).build()

}