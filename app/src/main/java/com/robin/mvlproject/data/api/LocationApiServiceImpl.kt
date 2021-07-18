package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.LocationResult
import io.reactivex.Single
import javax.inject.Inject

class LocationApiServiceImpl @Inject constructor(
    private val locationApiService: LocationApiService
) : LocationApiService {

    override fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult> {
        return locationApiService.getLocation(lat, lng, lang)
    }
}