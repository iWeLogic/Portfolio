package com.iwelogic.portfolio.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    var progress: MutableLiveData<Boolean> = MutableLiveData(false)
    var error: MutableLiveData<Boolean> = MutableLiveData(false)
    val close: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val warning: SingleLiveEvent<String> = SingleLiveEvent()

    fun onClickClose() {
      close.postValue(true)
    }

    fun onClickRetry() {
        reload()
    }

    open fun reload() {

    }
}