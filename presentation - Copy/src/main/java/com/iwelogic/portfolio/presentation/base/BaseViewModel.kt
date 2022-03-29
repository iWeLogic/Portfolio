package com.iwelogic.portfolio.presentation.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    var progress: MutableLiveData<Boolean> = MutableLiveData(false)
    var error: MutableLiveData<String> = MutableLiveData()
    val close: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val warning: SingleLiveEvent<String> = SingleLiveEvent()

    fun onClickClose() {
        close.postValue(true)
    }

    fun onClickRetry() {
        onReload()
    }

    open fun onReload() {
        Log.w("myLog", "onReload: ")
    }

    fun getBaseViewModel() : BaseViewModel = this
}