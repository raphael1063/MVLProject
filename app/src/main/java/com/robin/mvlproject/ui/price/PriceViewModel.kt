package com.robin.mvlproject.ui.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.BooksResult
import com.robin.mvlproject.data.entities.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _books = MutableLiveData<BooksResult>()
    val books: LiveData<BooksResult> = _books

    fun loadData(booksRequest: BooksRequest) {
        getBooks(booksRequest)
    }

    private fun getBooks(booksRequest: BooksRequest) {
        repository.getBooks(booksRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _books.value = it
            }, {
                Timber.d(it)
            }).addTo(compositeDisposable)
    }
}