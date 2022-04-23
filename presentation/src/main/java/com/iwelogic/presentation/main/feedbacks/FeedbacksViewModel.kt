package com.iwelogic.presentation.main.feedbacks

import com.iwelogic.presentation.base.BaseViewModel
import com.iwelogic.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbacksViewModel @Inject constructor() : BaseViewModel() {

    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickLogin() {
        openLogin.postValue(true)
    }
}
