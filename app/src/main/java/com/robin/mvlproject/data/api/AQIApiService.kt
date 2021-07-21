package com.robin.mvlproject.data.api

import com.robin.mvlproject.BuildConfig
import com.robin.mvlproject.data.entities.AQIResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface AQIApiService {

    @GET("/feed/geo:{lat};{lng}/?token=${BuildConfig.AQI_TOKEN}")
    fun getAQI(@Path("lat")lat: Double, @Path("lng")lng: Double) : Single<AQIResult>
}