package com.robin.mvlproject.di

import com.robin.mvlproject.BuildConfig
import com.robin.mvlproject.base.AQI
import com.robin.mvlproject.base.AQI_RETROFIT
import com.robin.mvlproject.base.LOCATION
import com.robin.mvlproject.base.LOCATION_RETROFIT
import com.robin.mvlproject.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named(AQI)
    fun provideBaseAQIUrl() = BuildConfig.AQI_URL

    @Provides
    @Named(LOCATION)
    fun provideBaseLocationUrl() = BuildConfig.LOCATION_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient
            .Builder()
            .callTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS).apply {
                if (BuildConfig.DEBUG) addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                )
            }.build()


    @Provides
    @Singleton
    @Named(AQI_RETROFIT)
    fun provideAQIRetrofit(
        okHttpClient: OkHttpClient,
        @Named(AQI) BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

    @Provides
    @Singleton
    @Named(LOCATION_RETROFIT)
    fun provideLocationRetrofit(
        okHttpClient: OkHttpClient,
        @Named(LOCATION) BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

    @Provides
    @Singleton
    fun provideAQIApiService(@Named(AQI_RETROFIT) retrofit: Retrofit): AQIApiService =
        retrofit.create(AQIApiService::class.java)

    @Provides
    @Singleton
    fun provideLocationApiService(@Named(LOCATION_RETROFIT) retrofit: Retrofit): LocationApiService =
        retrofit.create(LocationApiService::class.java)

    @Provides
    @Singleton
    fun provideBooksApiService(): ApiService =
        ApiServiceImpl()

}