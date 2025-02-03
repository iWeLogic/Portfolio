package com.iwelogic.profile.presentation.ui.profile

import androidx.compose.runtime.*
import androidx.lifecycle.*
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
            useCase.getHomeData()
                .onSuccess { result ->
                    _state.value = ProfileState.Main(
                        profile = result.profile.toProfile(),
                        contacts = result.contacts.map { it.toContact() },
                        jobs = result.jobs.map { it.toJob()},
                        studies = result.studies.map { it.toStudy()},
                    )
                }
                .onFailure {
                    _state.value = ProfileState.Error
                }
        }
    }
}