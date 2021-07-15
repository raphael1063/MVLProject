package com.robin.mvlproject.data

import com.robin.mvlproject.data.api.AQIApiHelper
import com.robin.mvlproject.data.entities.AQIResult
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(private val aqiApiHelper: AQIApiHelper) {

    fun getAQI(lat: Double, lng: Double): Single<AQIResult> {
        return aqiApiHelper.getAQI(lat, lng)
    }
}