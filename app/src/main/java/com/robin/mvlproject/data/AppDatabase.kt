package com.robin.mvlproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.robin.mvlproject.data.dao.LabelDao
import com.robin.mvlproject.data.entities.Book
import com.robin.mvlproject.data.entities.Label


@Database(entities = [Label::class, Book::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val labelDao: LabelDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "room_db").build()
        }
    }
}