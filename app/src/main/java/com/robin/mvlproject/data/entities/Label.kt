package com.robin.mvlproject.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.robin.mvlproject.data.entities.LabelType.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "label")
data class Label(
    var type: LabelType,

    @ColumnInfo(name = "locationInfo")
    var locationInfo: String,

    @ColumnInfo(name = "latitude")
    var latitude: Double,

    @ColumnInfo(name = "longitude")
    var longitude: Double,

    @ColumnInfo(name = "aqi")
    var aqi: Int,

    @ColumnInfo(name = "name")
    var name: String?
) : Parcelable {

    constructor(name: LabelType, locationInfo: String, aqi: Int, nickname: String?) : this(
        name,
        locationInfo,
        0.0,
        0.0,
        aqi,
        nickname
    )

    constructor(aqi: Int, lat: Double, lng: Double, name: String?) : this(
        UNDEFINED,
        "",
        lat,
        lng,
        aqi,
        name
    )

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    val idx: Long = 0L
}

enum class LabelType {
    A, B, UNDEFINED
}