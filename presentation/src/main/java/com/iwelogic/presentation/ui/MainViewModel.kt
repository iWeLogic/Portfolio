package com.iwelogic.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.LocalUserUseCase
import com.iwelogic.presentation.models.UserPresentation
import com.iwelogic.presentation.ui.main.profile.UserDomainPresentationMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var localUserUseCase: LocalUserUseCase, val mapper: UserDomainPresentationMapper) : ViewModel() {

    val user: MutableLiveData<UserPresentation> = MutableLiveData()

    init {
        checkIsLogged()
    }

    private fun checkIsLogged() {
        viewModelScope.launch {
            localUserUseCase.getUser().catch {
                it.printStackTrace()
            }.collect {
                user.postValue(mapper.map(it))
            }
        }
    }
}