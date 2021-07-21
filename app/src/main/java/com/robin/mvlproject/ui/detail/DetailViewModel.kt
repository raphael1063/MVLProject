package com.robin.mvlproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robin.mvlproject.base.BaseViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _label = MutableLiveData<Label>()
    val label: LiveData<Label> = _label

    private val _updateLabel = MutableLiveData<Label>()
    val updateLabel: LiveData<Label> = _updateLabel

    var nickname = MutableLiveData<String>()

    fun loadData(label: Label) {
        _label.value = label
        _label.value?.let {
            nickname.value = it.name ?: ""
        }

    }

    fun onSaveButtonClicked() {
        _label.value?.let {
            _updateLabel.value = it.apply {
                name = if (nickname.value.isNullOrEmpty()) null
                else nickname.value
            }
            updateLabel(it)
        }
    }

    private fun updateLabel(label: Label) {
        repository.updateLabelName(label)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("Nickname 업데이트 성공")
            }, {
                Timber.d("Nickname 업데이트 실패 ${it.message}")
            }).addTo(compositeDisposable)
    }
}