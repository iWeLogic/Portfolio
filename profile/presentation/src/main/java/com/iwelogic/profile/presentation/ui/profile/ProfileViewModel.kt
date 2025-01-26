package com.iwelogic.profile.presentation.ui.profile

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.iwelogic.profile.domain.repository.*
import com.iwelogic.profile.domain.use_case.*
import com.iwelogic.profile.presentation.mapper.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.*

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCase: ProfileUseCase) : ViewModel() {

    private val _state = mutableStateOf<ProfileState>(ProfileState.Loading)
    val state: State<ProfileState> = _state

    init {
        onReload()
    }

    private fun onReload() {
        _state.value = ProfileState.Loading
        viewModelScope.launch {
            useCase.getProfile()
                .onSuccess {
                    _state.value = ProfileState.Main(profile = it.toProfile())
                }
                .onFailure {
                    _state.value = ProfileState.Error
                }
        }
    }
}