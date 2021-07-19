package com.robin.mvlproject.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class Book(

    @PrimaryKey(autoGenerate = true)
    val idx: Long,

    @ColumnInfo(name = "positionA")
    val a: Label,

    @ColumnInfo(name = "positionB")
    val b: Label,

    @ColumnInfo(name = "price")
    val price: Int
)