package com.lwj.memorizer.data.api

import com.robin.mvlproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    val retrofit: Retrofit
        get() = provideRetrofit()

    val client: ApiService
        get() = provideRetrofitService()

    private fun provideRetrofitService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("BuildConfig.SERVER_URL")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttp3())
            .build()
    }

    private fun provideOkHttp3(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("X-FORWARDED-FOR", "")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access-token", "")
                    .addHeader("refresh-token", "")
                    .method(it.request().method, it.request().body)
                    .build()
                it.proceed(request)
            }
            .build()
    }
}