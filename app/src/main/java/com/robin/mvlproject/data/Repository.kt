package com.robin.mvlproject.data

import com.robin.mvlproject.data.entities.AQIResult
import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.BooksResult
import com.robin.mvlproject.data.entities.LocationResult
import io.reactivex.Single

interface Repository {

    fun getAQI(lat: Double, lng: Double): Single<AQIResult>

    fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult>

    fun getBooks(booksRequest: BooksRequest): Single<BooksResult>

    fun getHistory(year: String, month: String): Single<List<BooksResult>>
}