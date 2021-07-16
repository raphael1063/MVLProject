package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.*
import io.reactivex.Single
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val aqiApiService: AQIApiService,
    private val locationApiService: LocationApiService,
    private val booksApiService: BooksApiService
) : ApiHelper {

    override fun getAQI(lat: Double, lng: Double): Single<AQIResult> =
        aqiApiService.getAQI(lat, lng)

    override fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult> =
        locationApiService.getLocation(lat, lng, lang)

    override fun getBooks(booksRequest: BooksRequest): Single<BooksResult> =
        booksApiService.getBooks(booksRequest)
}