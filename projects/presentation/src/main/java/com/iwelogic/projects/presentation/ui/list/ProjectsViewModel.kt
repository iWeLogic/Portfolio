package com.iwelogic.projects.presentation.ui.list

import androidx.lifecycle.*
import com.iwelogic.core_ui.base.*
import com.iwelogic.projects.domain.use_case.*
import com.iwelogic.projects.presentation.mapper.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val useCase: ProjectsUseCase
) : BaseViewModel<ProjectsState, ProjectsIntent, ProjectsEvent>(initialState = ProjectsState.Loading) {

    init {
        onReload()
    }

    private fun onReload() {
        setState(ProjectsState.Loading)
        viewModelScope.launch {
            useCase.getProjects()
                .onSuccess {
                    setState(ProjectsState.Main(it.map { item -> item.toProject() }))
                }
                .onFailure {
                    setState(ProjectsState.Error)
                }
        }
    }

    override fun handleIntent(intent: ProjectsIntent) {
        when (intent) {
            is ProjectsIntent.OnClickReload -> onReload()
            is ProjectsIntent.OpenDetails -> sendEvent(ProjectsEvent.OpenProjectDetails(intent.id))
        }
    }
}