package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.AQIResult
import io.reactivex.Single

interface AQIApiHelper {

    fun getAQI(lat: Double, lng: Double): Single<AQIResult>
}