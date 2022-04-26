package com.iwelogic.presentation.ui.main.apps.app_details

import androidx.lifecycle.MutableLiveData
import com.iwelogic.presentation.models.AppPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor() : BaseViewModel() {

    val appPresentation: MutableLiveData<AppPresentation> = MutableLiveData()

    val openUrl: SingleLiveEvent<String> = SingleLiveEvent()

    fun onClickGooglePlay(){
        openUrl.postValue(appPresentation.value?.url)
    }
}
