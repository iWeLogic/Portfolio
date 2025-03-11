package com.iwelogic.projects.presentation.ui.list

import androidx.lifecycle.*
import com.iwelogic.core.base.datasource.*
import com.iwelogic.core_ui.base.*
import com.iwelogic.projects.domain.use_case.*
import com.iwelogic.projects.presentation.mapper.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

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
                .onSuccess { result ->
                    setState(ProjectsState.Main(result.map { item -> item.toProject() }))
                }
                .onFailure { failure ->
                    when (failure) {
                        is AppFailure.UnknownFailure -> setState(ProjectsState.Error)
                        else -> setState(ProjectsState.Error)
                    }
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