package com.robin.mvlproject.ui.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.robin.mvlproject.R
import com.robin.mvlproject.data.entities.MarkerButtonState
import com.robin.mvlproject.data.entities.MarkerButtonState.*

@BindingAdapter("setMarkerButton")
fun TextView.setMarkerButton(state: MarkerButtonState) {
    text = when(state) {
        NOTHING_SELECTED -> context.getString(R.string.set_a)
        A_SELECTED -> context.getString(R.string.set_b)
        B_SELECTED -> context.getString(R.string.set_a)
        BOTH_SELECTED -> context.getString(R.string.book)
    }
}
