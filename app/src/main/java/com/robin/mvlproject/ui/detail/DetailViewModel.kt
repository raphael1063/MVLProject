package com.robin.mvlproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robin.mvlproject.data.Repository
import com.robin.mvlproject.data.entities.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

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
                name = if(this@DetailViewModel.nickname.value.isNullOrEmpty()) {
                    null
                } else {
                    this@DetailViewModel.nickname.value
                }
            }
        }
    }
}