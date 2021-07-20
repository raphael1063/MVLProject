package com.robin.mvlproject.ui.home

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.Event
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.RepositoryImpl
import com.robin.mvlproject.data.entities.Book
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.data.entities.LabelType
import com.robin.mvlproject.data.entities.LabelType.*
import com.robin.mvlproject.data.entities.MarkerButtonState
import com.robin.mvlproject.data.entities.MarkerButtonState.*
import com.robin.mvlproject.ext.getFloor3
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.floor

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : BaseViewModel() {

    //대기질 지수
    private val _aqi = MutableLiveData<Int>()
    val aqi: LiveData<Int> = _aqi

    //마커 표시 상태 (NOTHING_SELECTED : A, B 선택되지 않음, A_SELECTED: A 레이블 선택됨, B_SELECTED: B 레이블 선택됨)
    private val _markerState = MutableLiveData(NOTHING_SELECTED)
    val markerState: LiveData<MarkerButtonState> = _markerState

    private val _labelA = MutableLiveData<Label>()
    val labelA: LiveData<Label> = _labelA

    private val _labelB = MutableLiveData<Label>()
    val labelB: LiveData<Label> = _labelB

    private val _moveCamera = MutableLiveData<List<Double>>()
    val moveCamera: LiveData<List<Double>> = _moveCamera

    private val _clearMap = MutableLiveData<Event<Unit>>()
    val clearMap: LiveData<Event<Unit>> = _clearMap

    private val _centerMarkerVisible = MutableLiveData(true)
    val centerMarkerVisible: LiveData<Boolean> = _centerMarkerVisible

    private val _actionLabelAClicked = MutableLiveData<Event<Label>>()
    val actionLabelAClicked: LiveData<Event<Label>> = _actionLabelAClicked

    private val _actionLabelBClicked = MutableLiveData<Event<Label>>()
    val actionLabelBClicked: LiveData<Event<Label>> = _actionLabelBClicked

    private val _actionEmptyLabelClicked = MutableLiveData<Event<LabelType>>()
    val actionEmptyLabelClicked: LiveData<Event<LabelType>> = _actionEmptyLabelClicked

    private val _actionBookClicked = MutableLiveData<Event<List<Label>>>()
    val actionBookClicked: LiveData<Event<List<Label>>> = _actionBookClicked

    private var currentLatitude = 0.0
    private var currentLongitude = 0.0
    private lateinit var currentLocation: String
    private var currentAQI = 0

    fun init(lat: Double, lng: Double) {
        getLabelByLatLng(lat, lng)
        currentLatitude = lat
        currentLongitude = lng
    }

    fun onCameraMove() {
        if (_markerState.value != B_SELECTED) {
            _centerMarkerVisible.value = true
        }
    }

    fun onCameraIdle(lat: Double, lng: Double) {
        getLabelByLatLng(lat, lng)
    }

    private fun getCurrentLocationInfo(lat: Double, lng: Double) {
        getAQI(lat, lng)
        getLocation(lat, lng, "en")
        currentLatitude = lat
        currentLongitude = lng
    }

    //해당위치의 대기질지수를 받아오는 API Call
    private fun getAQI(lat: Double, lng: Double) {
        repository.getAQI(lat, lng)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.status == "ok") {
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
            .subscribeOn(Schedulers.computation())
            .map { result ->
                //administrative 리스트를 내림차순으로 정렬
                val list = result.localityInfo.administrative.sortedWith(compareBy { -it.order })
                val locationInfo = "${list[1].name} ${list[0].name}"
                locationInfo
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ locationInfo ->
                currentLocation = locationInfo
            }, {
                Timber.d("Location FAILED!! = ${it.printStackTrace()}")
            }).addTo(compositeDisposable)
    }

    private fun insertLabel(label: Label) {
        label.latitude = label.latitude.getFloor3()
        label.longitude = label.longitude.getFloor3()
        repository.insertLabel(label)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                Timber.d("Label 저장 실패: ${it.message}")
            }).addTo(compositeDisposable)
    }

    private fun getLabelByLatLng(lat: Double, lng: Double) {
        repository.getLabelByLanLng(lat.getFloor3(), lng.getFloor3())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                currentLocation = it.locationInfo
                currentLatitude = it.latitude
                currentLongitude = it.longitude
                getAQI(lat, lng)
            }, {
                getCurrentLocationInfo(lat, lng)
            }).addTo(compositeDisposable)
    }

    //레이블 A 클릭
    fun onLabelAClicked() {
        _labelA.value?.let {
            _actionLabelAClicked.value = Event(it)
        } ?: run {
            _actionEmptyLabelClicked.value = Event(A)
        }
    }

    //레이블 B 클릭
    fun onLabelBClicked() {
        _labelB.value?.let {
            _actionLabelBClicked.value = Event(it)
        } ?: run {
            _actionEmptyLabelClicked.value = Event(B)
        }
    }

    fun onMarkerButtonClicked() {
        _centerMarkerVisible.value = false
        when (_markerState.value) {
            NOTHING_SELECTED -> {
                setLabel(currentLabel(A))
                _markerState.value = A_SELECTED
            }
            A_SELECTED -> {
                setLabel(currentLabel(B))
                _markerState.value = B_SELECTED
            }
            B_SELECTED -> {
                if (_labelA.value == null) {
                    setLabel(currentLabel(A))
                    _markerState.value = BOTH_SELECTED
                } else {
                    _actionBookClicked.value = Event(listOf(_labelA.value!!, _labelB.value!!))
                }
            }
            BOTH_SELECTED -> {
                _actionBookClicked.value = Event(listOf(_labelA.value!!, _labelB.value!!))
                Timber.d("BOOK CLICKED : labelA = ${_labelA.value} | labelB = ${_labelB.value}")
            }
        }
    }

    private fun setLabel(label: Label) {
        label.also {
            if (label.type == A) {
                _labelA.value = it
            } else {
                _labelB.value = it
            }
            insertLabel(it)
        }
    }

    private fun currentLabel(type: LabelType): Label {
        return Label(
            0,
            type,
            currentLocation,
            currentLatitude,
            currentLongitude,
            currentAQI,
            null
        )
    }

    //레이블 업데이트 (닉네임 변경)
    fun updateLabel(label: Label) {
        _clearMap.value = Event(Unit)
        if (label.type == A) {
            _labelA.value = label
            if (_labelB.value != null) {
                _labelB.value = _labelB.value
                _markerState.value = BOTH_SELECTED
            } else {
                _markerState.value = A_SELECTED
            }
        } else {
            _labelB.value = label
            if (_labelA.value != null) {
                _labelA.value = _labelA.value
                _markerState.value = BOTH_SELECTED
            } else {
                _markerState.value = B_SELECTED
            }
        }
    }

    fun moveToPosition(book: Book) {
        _clearMap.value = Event(Unit)
        updateLabel(book.a.apply {
            type = A
        })
        updateLabel(book.b.apply {
            type = B
        })
        val centerLat = (book.a.latitude + book.b.latitude) / 2
        val centerLng = (book.a.longitude + book.b.longitude) / 2
        moveCamera(centerLat, centerLng)
    }

    private fun moveCamera(lat: Double, lng: Double) {
        _moveCamera.value = listOf(lat, lng)
    }

    fun moveCamera() {
        _moveCamera.value = listOf(currentLatitude, currentLongitude)
    }
}