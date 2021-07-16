package com.robin.mvlproject.data.entities

import android.os.Parcelable
import com.robin.mvlproject.data.entities.LabelType.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Label(
    var name: LabelType,
    var locationInfo: String,
    var latitude: Double,
    var longitude: Double,
    var aqi: Int,
    var nickname: String?
) : Parcelable {
    constructor(name: LabelType, locationInfo: String, aqi: Int, nickname: String?) : this(name, locationInfo, 0.0, 0.0, aqi, nickname)
    constructor(aqi: Int, lat: Double, lng: Double, name: String?) : this(UNDEFINED, "", lat, lng, aqi, name)
}

enum class LabelType {
    A, B, UNDEFINED
}