package com.iwelogic.portfolio.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.portfolio.domain.UserExistUseCase
import com.iwelogic.portfolio.domain.models.ExistStatus
import com.iwelogic.portfolio.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var userExistUseCase: UserExistUseCase) : ViewModel() {

    val openMain: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun checkIsLogged() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, _ ->
                openLogin.postValue(true)
            }) {
            when (userExistUseCase.checkIsUserExist()) {
                ExistStatus.True -> openMain.postValue(true)
                ExistStatus.False -> openLogin.postValue(true)
            }
        }
    }
}