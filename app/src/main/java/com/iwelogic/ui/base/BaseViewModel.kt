package com.iwelogic.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T : BaseNavigator> : ViewModel() {

    var progress: MutableLiveData<Boolean> = MutableLiveData(false)
    var error: MutableLiveData<Boolean> = MutableLiveData(false)

    var navigator: T? = null

    fun onClickClose() {
        navigator?.close()
    }

    fun onClickRetry() {
        reload()
    }

    open fun reload() {

    }

    fun getBase(): BaseViewModel<T> = this
}