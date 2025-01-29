package com.iwelogic.projects.presentation.ui.details

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.iwelogic.projects.domain.use_case.*
import com.iwelogic.projects.presentation.mapper.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val useCase: ProjectsUseCase,
    savedState: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf<ProjectDetailsState>(ProjectDetailsState.Loading)
    val state: State<ProjectDetailsState> = _state
    private val id = savedState.get<String>("id")

    init {
        onReload()
    }

    private fun onReload() {
        _state.value = ProjectDetailsState.Loading
        viewModelScope.launch {
            useCase.getProject(id)
                .onSuccess {
                    _state.value = ProjectDetailsState.Main(it.toProject())
                }
                .onFailure {
                    _state.value = ProjectDetailsState.Error
                }
        }
    }
}