package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.BooksResult
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface BooksApiService {

    @POST("/books")
    fun getBooks(@Body requestBody: BooksRequest): Single<BooksResult>
}