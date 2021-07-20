package com.robin.mvlproject.ui.labellog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.Event
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.data.entities.LabelType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LabelLogViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _labelList = MutableLiveData<List<Label>>()
    val labelList: LiveData<List<Label>> = _labelList

    private val _actionLabelItemClicked = MutableLiveData<Event<Label>>()
    val actionLabelItemClicked: LiveData<Event<Label>> = _actionLabelItemClicked

    private lateinit var currentLabelType: LabelType

    fun loadData(labelType: LabelType) {
        currentLabelType = labelType
        getLabelList()
    }

    private fun getLabelList() {
        repository.getAllLabels()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _labelList.value = it
            }, {
                Timber.d("LabelListError! : ${it.message}")
            }).addTo(compositeDisposable)
    }

    fun onLabelItemClicked(label: Label) {
        _actionLabelItemClicked.value = Event(label.apply {
            type = currentLabelType
        })
    }

}