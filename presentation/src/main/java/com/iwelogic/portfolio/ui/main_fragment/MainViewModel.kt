package com.iwelogic.portfolio.ui.main_fragment

import androidx.lifecycle.viewModelScope
import com.iwelogic.portfolio.domain.main_fragment.LogoutUseCase
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.ui.base.BaseViewModel
import com.iwelogic.portfolio.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var logoutUseCase: LogoutUseCase) : BaseViewModel() {

    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickLogout(){
        viewModelScope.launch {
            logoutUseCase.logout().collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> openLogin.postValue(true)
                    is Result.Error -> warning.postValue(result.message)
                }
            }
        }
    }
}
