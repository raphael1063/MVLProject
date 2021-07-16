package com.robin.mvlproject.ui.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _labelA = MutableLiveData<Label>()
    val labelA: LiveData<Label> = _labelA

    private val _labelB = MutableLiveData<Label>()
    val labelB: LiveData<Label> = _labelB

    fun loadData(labelA: Label, labelB: Label) {
        _labelA.value = labelA
        _labelB.value = labelB
    }
}