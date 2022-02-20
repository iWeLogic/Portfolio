package com.iwelogic.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    var progress: MutableLiveData<Boolean> = MutableLiveData(false)
    var error: MutableLiveData<Boolean> = MutableLiveData(false)


    fun onClickClose() {
      //  navigator?.close()
    }

    fun onClickRetry() {
        reload()
    }

    open fun reload() {

    }
}