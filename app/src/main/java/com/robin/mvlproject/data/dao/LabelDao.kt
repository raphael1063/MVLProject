package com.robin.mvlproject.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.robin.mvlproject.data.entities.Label

interface LabelDao {

    @Insert
    fun insertLabel(label: Label)

    @Query("SELECT * FROM label")
    fun getAllLabels(labelList: List<Label>)

    @Query("SELECT * FROM label WHERE idx=:key")
    fun getLabel(key: Long)
}