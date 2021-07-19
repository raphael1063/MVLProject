package com.robin.mvlproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.robin.mvlproject.data.dao.BookDao
import com.robin.mvlproject.data.dao.LabelDao
import com.robin.mvlproject.data.entities.Book
import com.robin.mvlproject.data.entities.Label


@Database(entities = [Label::class, Book::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun labelDao(): LabelDao
    abstract fun bookDao(): BookDao
}