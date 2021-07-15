package com.robin.mvlproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.MarkerButtonState
import com.robin.mvlproject.data.entities.MarkerButtonState.*
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

    private val _location = MutableLiveData<String>()
    val location: LiveData<String> = _location

    private val _markerState = MutableLiveData(NOTHING_SELECTED)
    val markerState: LiveData<MarkerButtonState> = _markerState

    fun init(lat: Double, lng: Double) {
        getAQI(lat, lng)
        getLocation(lat, lng, "en")
    }

    fun onCameraMoved(lat: Double, lng: Double) {
        getAQI(lat, lng)
        getLocation(lat, lng, "en")
    }

    //해당위치의 대기질지수를 받아오는 API Call
    private fun getAQI(lat: Double, lng: Double) {
        repository.getAQI(lat, lng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.status == "ok") {
                    Timber.d("AQI RESULT!! = ${result.data.aqi}")
                    _aqi.value = result.data.aqi
                } else {
                    Timber.d("AQI RESULT!! = FAILED!")
                }
            }, {
                Timber.d("AQI FAILED!! = ${it.printStackTrace()}")
            }).addTo(compositeDisposable)
    }

    //위치정보를 받는 API Call
    private fun getLocation(lat: Double, lng: Double, lang: String) {
        repository.getLocation(lat, lng, lang)
            .subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.computation())
            .map { result ->
                //administrative 리스트를 내림차순으로 정렬
                val list = result.localityInfo.administrative.sortedWith(compareBy { -it.order })
                val address = "${list[1].name} ${list[0].name}"
                address
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ address ->
                Timber.d("Location RESULT!! = $address")
                _location.value = address
            }, {
                Timber.d("Location FAILED!! = ${it.printStackTrace()}")
            }).addTo(compositeDisposable)
    }


    fun onMarkerButtonClicked() {
        when(_markerState.value) {
            NOTHING_SELECTED -> {
                _markerState.value = A_SELECTED
            }
            A_SELECTED -> {
                _markerState.value = B_SELECTED
            }
            B_SELECTED -> {

            }
        }
    }
}