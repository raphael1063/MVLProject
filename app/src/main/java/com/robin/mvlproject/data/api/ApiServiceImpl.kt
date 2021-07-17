package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.BooksResult
import com.robin.mvlproject.data.entities.Label
import io.reactivex.Single

class ApiServiceImpl : ApiService {
    override fun getBooks(requestBody: BooksRequest): Single<BooksResult> {
        return Single.create { data ->
            data.onSuccess(
                BooksResult(
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

    override fun getHistory(year: String, month: String): Single<List<BooksResult>> {
        return Single.create { data ->
            data.onSuccess(
                listOf(
                    BooksResult(
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
                    BooksResult(
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
                    BooksResult(
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
                    BooksResult(
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