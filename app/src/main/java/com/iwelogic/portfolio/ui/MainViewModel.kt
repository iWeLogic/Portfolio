package com.iwelogic.portfolio.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.portfolio.domain.main.local_user.ExistStatus
import com.iwelogic.portfolio.domain.main.local_user.UserUseCase
import com.iwelogic.portfolio.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var userUseCase: UserUseCase) : ViewModel() {

    val openMain: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun checkIsLogged() {
        viewModelScope.launch {
            userUseCase.getExistStatus().collect {
                when (it) {
                    ExistStatus.True -> openMain.postValue(true)
                    ExistStatus.False -> openLogin.postValue(true)
                }
            }
        }
    }
}