package com.robin.mvlproject.ui.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.Event
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.RepositoryImpl
import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.Book
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.data.entities.LabelType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : BaseViewModel() {

    private val _locationA = MutableLiveData<String>()
    val locationA: LiveData<String> = _locationA

    private val _locationB = MutableLiveData<String>()
    val locationB: LiveData<String> = _locationB

    private val _books = MutableLiveData<Book>()
    val books: LiveData<Book> = _books

    private val _actionHistoryButtonClicked = MutableLiveData<Event<Unit>>()
    val actionHistoryButtonClicked: LiveData<Event<Unit>> = _actionHistoryButtonClicked

    fun loadData(labelA: Label, labelB: Label) {
        getBooks(BooksRequest(labelA, labelB))
    }

    private fun getBooks(booksRequest: BooksRequest) {
        repository.getBooks(booksRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _books.value = it
                getLocation(it.a.latitude, it.a.longitude, LabelType.A)
                getLocation(it.b.latitude, it.b.longitude, LabelType.B)
            }, {
                Timber.d(it)
            }).addTo(compositeDisposable)
    }

    //위치정보를 받는 API Call
    private fun getLocation(lat: Double, lng: Double, type: LabelType) {
        repository.getLocation(lat, lng, "en")
            .subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.computation())
            .map { result ->
                //administrative 리스트를 내림차순으로 정렬
                val list = result.localityInfo.administrative.sortedWith(compareBy { -it.order })
                val locationInfo = "${list[1].name} ${list[0].name}"
                locationInfo
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ locationInfo ->
                Timber.d("Location RESULT!! = $locationInfo")
                if(type == LabelType.A) {
                    _locationA.value = locationInfo
                } else {
                    _locationB.value = locationInfo
                }
            }, {
                Timber.d("Location FAILED!! = ${it.printStackTrace()}")
            }).addTo(compositeDisposable)
    }

    fun onHistoryButtonClicked() {
        _actionHistoryButtonClicked.value = Event(Unit)
    }
}