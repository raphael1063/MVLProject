package com.robin.mvlproject.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.robin.mvlproject.data.entities.Label
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LabelDao {

    @Insert
    fun insertLabel(label: Label) : Completable

    @Query("SELECT * FROM label")
    fun getAllLabels() : Single<List<Label>>

    @Query("SELECT * FROM label WHERE idx=:key")
    fun getLabel(key: Long) : Single<Label>

    @Query("SELECT * FROM label WHERE latitude=:lat AND longitude=:lng")
    fun getLabelByLanLng(lat: Double, lng: Double): Single<Label>

    @Query("SELECT COUNT(*) FROM label")
    fun getTableRowCount(): Single<Long>

    @Update
    fun updateLabelName(label: Label): Completable
}