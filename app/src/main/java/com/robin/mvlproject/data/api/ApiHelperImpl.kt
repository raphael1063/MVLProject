package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.AQIResult
import com.robin.mvlproject.data.entities.LocationResult
import io.reactivex.Single
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val aqiApiService: AQIApiService,
    private val locationApiService: LocationApiService
) : ApiHelper {

    override fun getAQI(lat: Double, lng: Double): Single<AQIResult> {
        return aqiApiService.getAQI(lat, lng)
    }

    override fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult> {
        return locationApiService.getLocation(lat, lng, lang)
    }
}