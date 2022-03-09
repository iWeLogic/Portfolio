package com.iwelogic.portfolio.ui.news

import androidx.lifecycle.MutableLiveData
import com.iwelogic.portfolio.ui.base.BaseViewModel
import com.iwelogic.portfolio.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : BaseViewModel() {

    val openDetails: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickOpenDetails(){
        openDetails.postValue(true)
    }
}
