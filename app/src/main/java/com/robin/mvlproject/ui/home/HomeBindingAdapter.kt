package com.robin.mvlproject.ui.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.robin.mvlproject.data.entities.MarkerButtonState
import com.robin.mvlproject.data.entities.MarkerButtonState.*

@BindingAdapter("setMarkerButton")
fun TextView.setMarkerButton(state: MarkerButtonState) {
    text = when(state) {
        NOTHING_SELECTED -> "Set A"
        A_SELECTED -> "Set B"
        B_SELECTED -> "Book"
    }
}
