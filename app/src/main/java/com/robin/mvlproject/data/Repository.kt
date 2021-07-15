package com.robin.mvlproject.data

import com.robin.mvlproject.data.api.ApiHelper
import com.robin.mvlproject.data.entities.AQIResult
import com.robin.mvlproject.data.entities.LocationResult
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getAQI(lat: Double, lng: Double): Single<AQIResult> {
        return apiHelper.getAQI(lat, lng)
    }

    fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult> {
        return apiHelper.getLocation(lat, lng, lang)
    }
}