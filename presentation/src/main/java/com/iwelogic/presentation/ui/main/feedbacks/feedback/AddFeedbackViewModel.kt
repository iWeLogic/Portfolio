package com.iwelogic.presentation.ui.main.feedbacks.feedback

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.iwelogic.presentation.models.FeedbackPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFeedbackViewModel @Inject constructor() : BaseViewModel() {

    val addFeedback: SingleLiveEvent<FeedbackPresentation> = SingleLiveEvent()
    val feedback: MutableLiveData<FeedbackPresentation> = MutableLiveData(FeedbackPresentation())


    fun onClickOk() {
        Log.w("myLog", "onClickOk: " + feedback.value)
        addFeedback.postValue(feedback.value)
        close.postValue(true)
    }
}
