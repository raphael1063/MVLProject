package com.robin.mvlproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.Event
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.data.entities.LabelType.*
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

    //대기질 지수
    private val _aqi = MutableLiveData<Int>()
    val aqi: LiveData<Int> = _aqi


    private val _markerState = MutableLiveData(NOTHING_SELECTED)
    val markerState: LiveData<MarkerButtonState> = _markerState

    private val _labelA = MutableLiveData<Label>()
    val labelA: LiveData<Label> = _labelA

    private val _labelB = MutableLiveData<Label>()
    val labelB: LiveData<Label> = _labelB

    private val _centerMarkerVisible = MutableLiveData<Boolean>()
    val centerMarkerVisible: LiveData<Boolean> = _centerMarkerVisible

    private val _actionLabelAClicked = MutableLiveData<Event<Label>>()
    val actionLabelAClicked: LiveData<Event<Label>> = _actionLabelAClicked

    private val _actionLabelBClicked = MutableLiveData<Event<Label>>()
    val actionLabelBClicked: LiveData<Event<Label>> = _actionLabelBClicked

    private val _actionBookClicked = MutableLiveData<Event<List<Label>>>()
    val actionBookClicked: LiveData<Event<List<Label>>> = _actionBookClicked

    private var currentLatitude = 0.0
    private var currentLongitude = 0.0
    private lateinit var currentLocation: String
    private var currentAQI = 0

    fun init(lat: Double, lng: Double) {
        getAQI(lat, lng)
        getLocation(lat, lng, "en")
        currentLatitude = lat
        currentLongitude = lng
    }

    fun onCameraMoved(lat: Double, lng: Double) {
        getAQI(lat, lng)
        getLocation(lat, lng, "en")
        currentLatitude = lat
        currentLongitude = lng
        if (_markerState.value != B_SELECTED) {
            _centerMarkerVisible.value = true
        }
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
                    currentAQI = result.data.aqi
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
                val locationInfo = "${list[1].name} ${list[0].name}"
                locationInfo
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ locationInfo ->
                Timber.d("Location RESULT!! = $locationInfo")
                currentLocation = locationInfo
            }, {
                Timber.d("Location FAILED!! = ${it.printStackTrace()}")
            }).addTo(compositeDisposable)
    }

    //레이블 A 클릭
    fun onLabelAClicked() {
        _labelA.value?.let {
            _actionLabelAClicked.value = Event(it)
        }
    }

    //레이블 B 클릭
    fun onLabelBClicked() {
        _labelB.value?.let {
            _actionLabelBClicked.value = Event(it)
        }
    }

    fun onMarkerButtonClicked() {
        _centerMarkerVisible.value = false
        when (_markerState.value) {
            NOTHING_SELECTED -> {
                _markerState.value = A_SELECTED
                _labelA.value =
                    Label(A, currentLocation, currentLatitude, currentLongitude, currentAQI, null)
            }
            A_SELECTED -> {
                _markerState.value = B_SELECTED
                _labelB.value =
                    Label(B, currentLocation, currentLatitude, currentLongitude, currentAQI, null)
            }
            B_SELECTED -> {
                if (_labelA.value != null && _labelB.value != null) {
                    _actionBookClicked.value = Event(listOf(_labelA.value!!, _labelB.value!!))
                }
            }
        }
    }

    fun updateLabel(label: Label) {
        if (label.type == A) {
            _labelA.value = label
        } else {
            _labelB.value = label
        }
    }
}