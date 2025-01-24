package com.iwelogic.projects.presentation.ui.details

import android.util.*
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
    private val savedState: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf<ProjectDetailsState>(ProjectDetailsState.Loading)
    val state: State<ProjectDetailsState> = _state

    init {
        val id = savedState.get<String>("id")
        onReload(id)
    }

    private fun onReload(id: String?) {
        Log.w("myLog", "onReload: ${id}")
        _state.value = ProjectDetailsState.Loading
        viewModelScope.launch {
            useCase.getProject(id)
                .onSuccess {
                    Log.w("myLog", "onReload: ${it.toProject().title}")
                    _state.value = ProjectDetailsState.Main(it.toProject())
                }
                .onFailure {
                    _state.value = ProjectDetailsState.Error
                }
        }
    }
}