package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.Book
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/books")
    fun getBooks(@Body requestBody: BooksRequest): Single<Book>

    @GET("/books")
    fun getHistory(@Query("year") year: String, @Query("month") month: String): Single<List<Book>>
}