package com.robin.mvlproject.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.Event
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _historyList = MutableLiveData<List<Book>>()
    val historyList: LiveData<List<Book>> = _historyList

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private val _actionHistoryItemClick = MutableLiveData<Event<Book>>()
    val actionHistoryItemClick: LiveData<Event<Book>> = _actionHistoryItemClick

    init {
        getHistory("2021", "7")
    }

    private fun getHistory(year: String, month: String) {
        repository.getHistory(year, month)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _historyList.value = it
                _totalCount.value = it.size
                var price = 0
                for(i in it.indices) {
                    price += it[i].price
                }
                _totalPrice.value = price
            }, {

            }).addTo(compositeDisposable)
    }

    fun onHistoryItemClicked(book: Book) {
        _actionHistoryItemClick.value = Event(book)
    }
}