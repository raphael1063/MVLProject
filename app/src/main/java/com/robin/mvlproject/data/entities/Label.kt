package com.robin.mvlproject.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Label(
    val name: LabelType,
    val locationInfo: String,
    val aqi: Int,
    var nickname: String?
) : Parcelable

enum class LabelType {
    A, B
}