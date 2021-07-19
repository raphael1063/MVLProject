package com.robin.mvlproject.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.robin.mvlproject.data.entities.Book
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BookDao {

    @Insert
    fun insertBook(book: Book): Completable

    @Query("SELECT * FROM Book")
    fun getAllBooks() : Single<List<Book>>

    @Query("SELECT * FROM Book WHERE idx=:key")
    fun getBook(key: Long) : Single<Book>
}