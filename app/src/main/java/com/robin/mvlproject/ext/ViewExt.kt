package com.robin.mvlproject.ext

import android.view.View
import androidx.databinding.BindingAdapter
import com.robin.mvlproject.util.OnSafeClickListener

@BindingAdapter("onSafeClick")
fun View.setOnSafeClick(clickListener: View.OnClickListener?) {
    setOnClickListener(OnSafeClickListener(clickListener))
}