package com.iwelogic.projects.presentation.ui.list

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.iwelogic.projects.domain.use_case.*
import com.iwelogic.projects.presentation.mapper.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val useCase: ProjectsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<ProjectsState>(ProjectsState.Loading)
    val state: State<ProjectsState> = _state

    init {
        onReload()
    }

    private fun onReload() {
        _state.value = ProjectsState.Loading
        viewModelScope.launch {
            useCase.getProjects()
                .onSuccess {
                    _state.value = ProjectsState.Main(it.map { item -> item.toProject() })
                }
                .onFailure {
                    _state.value = ProjectsState.Error
                }
        }
    }
}