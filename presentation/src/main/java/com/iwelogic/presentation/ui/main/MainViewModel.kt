package com.iwelogic.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    val openProfile: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val title: MutableLiveData<String> = MutableLiveData()

    fun onClickProfile() {
        openProfile.postValue(true)
    }
}