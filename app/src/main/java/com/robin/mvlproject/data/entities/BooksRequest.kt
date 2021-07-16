package com.robin.mvlproject.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BooksRequest(
    val a: Label,
    val b: Label
) : Parcelable