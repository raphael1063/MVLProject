package com.robin.mvlproject.ui.main

import androidx.lifecycle.SavedStateHandle
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel() {
}