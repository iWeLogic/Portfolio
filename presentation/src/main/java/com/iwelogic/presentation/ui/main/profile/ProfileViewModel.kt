package com.iwelogic.presentation.ui.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.main.profile.LogoutUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.models.UserPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.PopupData
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(var logoutUseCase: LogoutUseCase) : BaseViewModel() {

    var user: MutableLiveData<UserPresentation> = MutableLiveData()
    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickLogout() {
        viewModelScope.launch {
            logoutUseCase.logout().collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> {
                        user.postValue(null)
                        close.postValue(true)
                    }
                    is Result.Error -> showPopup.postValue(PopupData(text = result.message))
                }
            }
        }
    }

    fun onClickSave() {

    }

    fun onClickLogin() {
        openLogin.postValue(true)
    }
}
