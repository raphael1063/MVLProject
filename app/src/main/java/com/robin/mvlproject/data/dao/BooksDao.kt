package com.robin.mvlproject.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.robin.mvlproject.data.entities.Book

interface BooksDao {

    @Insert
    fun insertBook(book: Book)

    @Query("SELECT * FROM Book")
    fun getAllBooks(bookList: List<Book>)

    @Query("SELECT * FROM Book WHERE idx=:key")
    fun getBook(key: Long)
}