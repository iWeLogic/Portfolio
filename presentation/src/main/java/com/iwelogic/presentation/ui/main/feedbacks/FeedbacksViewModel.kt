package com.iwelogic.presentation.ui.main.feedbacks

import androidx.lifecycle.MutableLiveData
import com.iwelogic.presentation.models.UserPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbacksViewModel @Inject constructor() : BaseViewModel() {

    var user: MutableLiveData<UserPresentation> = MutableLiveData()
    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickLogin() {
        openLogin.postValue(true)
    }
}
