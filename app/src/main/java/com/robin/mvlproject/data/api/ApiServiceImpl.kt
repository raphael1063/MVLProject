package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.Book
import com.robin.mvlproject.data.entities.Label
import io.reactivex.Single

class ApiServiceImpl : ApiService {
    override fun getBooks(requestBody: BooksRequest): Single<Book> {
        return Single.create { data ->
            data.onSuccess(
                Book(
                    0,
                    Label(
                        requestBody.a.aqi,
                        requestBody.a.latitude,
                        requestBody.a.longitude,
                        requestBody.a.name
                    ),
                    Label(
                        requestBody.b.aqi,
                        requestBody.b.latitude,
                        requestBody.b.longitude,
                        requestBody.b.name
                    ), 77777
                )
            )
        }
    }

    override fun getHistory(year: String, month: String): Single<List<Book>> {
        return Single.create { data ->
            data.onSuccess(
                listOf(
                    Book(
                        0,
                        Label(
                            40,
                            36.564,
                            127.001,
                            "서울시 강남구"
                        ),
                        Label(
                            40,
                            36.564,
                            127.001,
                            "서울시 강남구"
                        ),
                        10000
                    ),
                    Book(
                        1,
                        Label(
                            40,
                            36.564,
                            127.001,
                            "서울시 강남구"
                        ),
                        Label(
                            40,
                            36.564,
                            127.001,
                            "서울시 강남구"
                        ),
                        10000
                    ),
                    Book(
                        2,
                        Label(
                            40,
                            36.564,
                            127.001,
                            "서울시 강남구"
                        ),
                        Label(
                            40,
                            36.564,
                            127.001,
                            "서울시 강남구"
                        ),
                        10000
                    ),
                    Book(
                        3,
                        Label(
                            40,
                            36.564,
                            127.001,
                            "서울시 강남구"
                        ),
                        Label(
                            40,
                            36.564,
                            127.001,
                            "서울시 강남구"
                        ),
                        10000
                    )
                )
            )
        }
    }
}