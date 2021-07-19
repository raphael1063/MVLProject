package com.robin.mvlproject.data

import com.robin.mvlproject.data.api.*
import com.robin.mvlproject.data.dao.BookDao
import com.robin.mvlproject.data.dao.LabelDao
import com.robin.mvlproject.data.entities.*
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val aqiApiService: AQIApiService,
    private val locationApiService: LocationApiService,
    private val apiService: ApiService,
    private val labelDao: LabelDao,
    private val bookDao: BookDao
) : Repository {

    override fun getAQI(lat: Double, lng: Double): Single<AQIResult> =
        aqiApiService.getAQI(lat, lng)

    override fun getLocation(lat: Double, lng: Double, lang: String): Single<LocationResult> =
        locationApiService.getLocation(lat, lng, lang)

    override fun getBooks(booksRequest: BooksRequest): Single<Book> =
        apiService.getBooks(booksRequest)

    override fun getHistory(year: String, month: String): Single<List<Book>> =
        apiService.getHistory(year, month)

    override fun insertLabel(label: Label): Completable =
        labelDao.insertLabel(label)

    override fun getAllLabels(): Single<List<Label>> =
        labelDao.getAllLabels()

    override fun getLabel(key: Long): Single<Label> =
        labelDao.getLabel(key)

    override fun getLabelByLanLng(lat: Double, lng: Double): Single<Label> =
        labelDao.getLabelByLanLng(lat, lng)

    override fun updateLabel(label: Label): Completable =
        labelDao.updateLabelName(label)

    override fun insertBook(book: Book): Completable =
        bookDao.insertBook(book)

    override fun getAllBooks(): Single<List<Book>> =
        bookDao.getAllBooks()

    override fun getBook(key: Long): Single<Book> =
        bookDao.getBook(key)
}