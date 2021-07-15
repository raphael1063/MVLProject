package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.LocationResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApiService {

    @GET("/data/reverse-geocode-client")
    fun getLocation(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("localityLanguage") lang: String
    ): Single<LocationResult>
}