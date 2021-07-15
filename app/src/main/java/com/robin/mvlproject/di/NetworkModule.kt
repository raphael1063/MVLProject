package com.robin.mvlproject.di

import android.location.Location
import com.robin.mvlproject.BuildConfig
import com.robin.mvlproject.data.api.ApiHelper
import com.robin.mvlproject.data.api.ApiHelperImpl
import com.robin.mvlproject.data.api.AQIApiService
import com.robin.mvlproject.data.api.LocationApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    @Named("AQI")
    fun provideBaseAQIUrl() = BuildConfig.AQI_URL

    @Provides
    @Named("Location")
    fun provideBaseLocationUrl() = BuildConfig.LOCATION_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .callTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .callTimeout(40, TimeUnit.SECONDS)
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    @Named("AQIRetrofit")
    fun provideAQIRetrofit(
        okHttpClient: OkHttpClient,
        @Named("AQI") BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("LocationRetrofit")
    fun provideLocationRetrofit(
        okHttpClient: OkHttpClient,
        @Named("Location") BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAQIApiService(@Named("AQIRetrofit") retrofit: Retrofit): AQIApiService =
        retrofit.create(AQIApiService::class.java)

    @Provides
    @Singleton
    fun provideLocationApiService(@Named("LocationRetrofit") retrofit: Retrofit): LocationApiService =
        retrofit.create(LocationApiService::class.java)

    @Provides
    @Singleton
    fun provideAQIApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}