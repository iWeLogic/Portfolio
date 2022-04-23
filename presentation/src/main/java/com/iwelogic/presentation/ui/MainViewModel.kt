package com.iwelogic.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.UserExistUseCase
import com.iwelogic.domain.models.ExistStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var userExistUseCase: UserExistUseCase) : ViewModel() {

    val userExistStatus: MutableLiveData<Boolean> = MutableLiveData()

    init {
        checkIsLogged()
    }

    private fun checkIsLogged() {
        viewModelScope.launch {
            userExistUseCase.checkIsUserExist().catch {
                it.printStackTrace()
            }.collect {
                userExistStatus.postValue(it == ExistStatus.True)
            }
        }
    }
}