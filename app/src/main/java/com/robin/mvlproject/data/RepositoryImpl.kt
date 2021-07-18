package com.robin.mvlproject.data

import com.robin.mvlproject.data.api.*
import com.robin.mvlproject.data.entities.AQIResult
import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.Book
import com.robin.mvlproject.data.entities.LocationResult
import io.reactivex.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val aqiApiService: AQIApiService,
    private val locationApiService: LocationApiService,
    private val apiService: ApiService
) : Repository {

    override fun getAQI(lat: Double, lng: Double): Single<AQIResult> =
        aqiApiService.getAQI(lat, lng)

    override fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult> =
        locationApiService.getLocation(lat, lng, lang)

    override fun getBooks(booksRequest: BooksRequest): Single<Book> =
        apiService.getBooks(booksRequest)

    override fun getHistory(year: String, month: String): Single<List<Book>> =
        apiService.getHistory(year, month)
}