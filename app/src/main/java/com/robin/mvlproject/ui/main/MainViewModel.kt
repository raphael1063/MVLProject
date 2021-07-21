package com.robin.mvlproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.Event
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.entities.Book
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.data.entities.LabelType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _openLabelLog = MutableLiveData<Event<LabelType>>()
    val openLabelLog: LiveData<Event<LabelType>> = _openLabelLog

    private val _openDetail = MutableLiveData<Event<Label>>()
    val openDetail: LiveData<Event<Label>> = _openDetail

    private val _openPrice = MutableLiveData<Event<List<Label>>>()
    val openPrice: LiveData<Event<List<Label>>> = _openPrice

    private val _openHistory = MutableLiveData<Event<Unit>>()
    val openHistory: LiveData<Event<Unit>> = _openHistory

    private val _updateLabel = MutableLiveData<Label>()
    val updateLabel: LiveData<Label> = _updateLabel

    private val _moveToPosition = MutableLiveData<Book>()
    val moveToPosition: LiveData<Book> = _moveToPosition

    fun openLabelLog(labelType: LabelType) {
        _openLabelLog.value = Event(labelType)
    }

    fun openDetail(label: Label) {
        _openDetail.value = Event(label)
    }

    fun openPrice(list: List<Label>) {
        _openPrice.value = Event(list)
    }

    fun openHistory() {
        _openHistory.value = Event(Unit)
    }

    fun updateLabel(label: Label) {
        _updateLabel.value = label
    }

    fun moveToPosition(book: Book) {
        _moveToPosition.value = book
    }
}