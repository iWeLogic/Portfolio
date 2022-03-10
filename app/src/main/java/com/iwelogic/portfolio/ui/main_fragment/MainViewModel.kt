package com.iwelogic.portfolio.ui.main_fragment

import com.iwelogic.portfolio.ui.base.BaseViewModel
import com.iwelogic.portfolio.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    val openProfile: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickProfile(){
        openProfile.postValue(true)
    }
}
