package com.iwelogic.projects.presentation.ui.details

import androidx.lifecycle.*
import com.iwelogic.core.Const.KEY_ID
import com.iwelogic.core_ui.base.*
import com.iwelogic.projects.domain.use_case.*
import com.iwelogic.projects.presentation.mapper.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val useCase: ProjectsUseCase,
    savedState: SavedStateHandle,
) : BaseViewModel<ProjectDetailsState, ProjectDetailsEvent, ProjectDetailsUIEffect>(ProjectDetailsState.Loading) {

    private val id = savedState.get<String>(KEY_ID)

    init {
        onReload()
    }

    private fun onReload() {
        setState(ProjectDetailsState.Loading)
        viewModelScope.launch {
            useCase.getProject(id)
                .onSuccess {
                    setState(ProjectDetailsState.Main(it.toProject()))
                }
                .onFailure {
                    setState(ProjectDetailsState.Error)
                }
        }
    }

    override fun obtainEvent(userEvent: ProjectDetailsEvent) {
        when (userEvent) {
            ProjectDetailsEvent.OnClickReload -> onReload()
        }
    }
}