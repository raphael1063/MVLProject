package com.robin.mvlproject.ui.price

import androidx.lifecycle.SavedStateHandle
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel() {
}