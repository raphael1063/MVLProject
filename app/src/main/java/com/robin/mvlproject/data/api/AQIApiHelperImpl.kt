package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.AQIResult
import io.reactivex.Single
import javax.inject.Inject

class AQIApiHelperImpl @Inject constructor(private val aqiApiService: AQIApiService) : AQIApiHelper{
    override fun getAQI(lat: Double, lng: Double): Single<AQIResult> {
        return aqiApiService.getAQI(lat, lng)
    }
}