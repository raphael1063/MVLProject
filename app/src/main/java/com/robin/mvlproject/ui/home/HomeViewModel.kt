package com.robin.mvlproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _aqi = MutableLiveData<Int>()
    val aqi: LiveData<Int> = _aqi

    init {
        getAQI(37.4769, 126.9562)
    }

    fun getAQI(lat: Double, lng: Double) {
        repository.getAQI(lat, lng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.status == "ok") {
                    Timber.d("RESULT!! = ${result.data.aqi}")
                    _aqi.value = result.data.aqi
                } else {
                    Timber.d("RESULT!! = FAILED!")
                }
            }, {
                Timber.d("FAILED!! = ${it.printStackTrace()}")
            }).addTo(compositeDisposable)
    }
}