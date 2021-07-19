package com.robin.mvlproject.data.entities

import android.os.Parcelable
import androidx.room.*
import com.robin.mvlproject.data.Converters
import com.robin.mvlproject.data.entities.LabelType.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "label")
data class Label(

    @PrimaryKey(autoGenerate = true)
    val idx: Long,

    @ColumnInfo(name = "labelType")
    var type: LabelType?,

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
    constructor(
        aqi: Int,
        latitude: Double,
        longitude: Double,
        locationInfo: String,
        name: String?
    ) : this(0, UNDEFINED, locationInfo, latitude, longitude, aqi, name)
}