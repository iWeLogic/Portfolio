package com.iwelogic.presentation.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    var progress: MutableLiveData<Boolean> = MutableLiveData(false)
    var error: MutableLiveData<String> = MutableLiveData()
    val close: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val hideKeyboard: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val showPopup: SingleLiveEvent<PopupData> = SingleLiveEvent()
    val showToast: SingleLiveEvent<String> = SingleLiveEvent()

    fun onClickClose() {
        close.postValue(true)
    }

    fun onClickRetry() {
        onReload()
    }

    open fun onReload() {
        Log.w("myLog", "onReload: ")
    }

    fun getBaseViewModel(): BaseViewModel = this
}