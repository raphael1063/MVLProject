package com.robin.mvlproject.data

import com.robin.mvlproject.data.entities.*
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {

    fun getAQI(lat: Double, lng: Double): Single<AQIResult>

    fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult>

    fun getBooks(booksRequest: BooksRequest): Single<Book>

    fun getHistory(year: String, month: String): Single<List<Book>>

    fun insertLabel(label: Label): Completable

    fun getAllLabels(): Single<List<Label>>

    fun getLabel(key: Long) : Single<Label>

    fun getLabelByLanLng(lat: Double, lng: Double): Single<Label>

    fun updateLabelName(label: Label): Completable

    fun getTableRowCount(): Single<Long>

    fun insertBook(book: Book) : Completable

    fun getAllBooks(): Single<List<Book>>

    fun getBook(key: Long): Single<Book>
}