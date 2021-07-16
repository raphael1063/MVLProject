package com.robin.mvlproject.data

import com.robin.mvlproject.data.api.ApiHelper
import com.robin.mvlproject.data.entities.AQIResult
import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.BooksResult
import com.robin.mvlproject.data.entities.LocationResult
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getAQI(lat: Double, lng: Double): Single<AQIResult> =
        apiHelper.getAQI(lat, lng)

    fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult> =
        apiHelper.getLocation(lat, lng, lang)

    fun getBooks(booksRequest: BooksRequest): Single<BooksResult> =
        apiHelper.getBooks(booksRequest)
}