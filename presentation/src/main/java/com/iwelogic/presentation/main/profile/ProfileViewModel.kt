package com.iwelogic.presentation.main.profile

import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.main.profile.LogoutUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.base.BaseViewModel
import com.iwelogic.presentation.base.PopupData
import com.iwelogic.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(var logoutUseCase: LogoutUseCase) : BaseViewModel() {

    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickLogout() {
        viewModelScope.launch {
            logoutUseCase.logout().collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> openLogin.postValue(true)
                    is Result.Error -> showPopup.postValue(PopupData(text = result.message))
                }
            }
        }
    }
}
