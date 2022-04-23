package com.iwelogic.presentation.ui.main.apps.app_details

import androidx.lifecycle.MutableLiveData
import com.iwelogic.presentation.models.PresentationApp
import com.iwelogic.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor() : BaseViewModel() {

    val app: MutableLiveData<PresentationApp> = MutableLiveData()
}
