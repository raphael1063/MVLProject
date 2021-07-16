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
                    Label(
                        requestBody.a.aqi,
                        requestBody.a.latitude,
                        requestBody.a.longitude,
                        requestBody.a.nickname
                    ),
                    Label(
                        requestBody.b.aqi,
                        requestBody.b.latitude,
                        requestBody.b.longitude,
                        requestBody.b.nickname
                    ), 77777
                )
            )
        }
    }
}