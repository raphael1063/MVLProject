package com.robin.mvlproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.robin.mvlproject.Event
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.data.entities.Step
import com.robin.mvlproject.data.entities.Step.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _openDetail = MutableLiveData<Event<Label>>()
    val openDetail: LiveData<Event<Label>> = _openDetail

    private val _openPrice = MutableLiveData<Event<BooksRequest>>()
    val openPrice: LiveData<Event<BooksRequest>> = _openPrice

    private val _openHistory = MutableLiveData<Event<Unit>>()
    val openHistory: LiveData<Event<Unit>> = _openHistory

    private val _updateLabel = MutableLiveData<Label>()
    val updateLabel: LiveData<Label> = _updateLabel

    fun openDetail(label: Label) {
        _openDetail.value = Event(label)
    }

    fun openPrice(model: BooksRequest) {
        _openPrice.value = Event(model)
    }

    fun openHistory() {
        _openHistory.value = Event(Unit)
    }

    fun updateLabel(label: Label) {
        _updateLabel.value = label
    }
}