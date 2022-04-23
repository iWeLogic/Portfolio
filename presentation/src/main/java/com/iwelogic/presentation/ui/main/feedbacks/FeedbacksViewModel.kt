package com.iwelogic.presentation.ui.main.feedbacks

import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbacksViewModel @Inject constructor() : BaseViewModel() {

    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickLogin() {
        openLogin.postValue(true)
    }
}
