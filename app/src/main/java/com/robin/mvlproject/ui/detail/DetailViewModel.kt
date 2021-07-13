package com.robin.mvlproject.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.robin.mvlproject.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {
}