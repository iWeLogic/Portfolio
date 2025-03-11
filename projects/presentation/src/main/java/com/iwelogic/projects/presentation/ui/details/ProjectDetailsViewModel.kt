package com.iwelogic.projects.presentation.ui.details

import androidx.lifecycle.*
import com.iwelogic.core.Const.KEY_ID
import com.iwelogic.core.base.datasource.*
import com.iwelogic.core_ui.base.*
import com.iwelogic.projects.domain.use_case.*
import com.iwelogic.projects.presentation.mapper.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val useCase: ProjectsUseCase,
    savedState: SavedStateHandle,
) : BaseViewModel<ProjectDetailsState, ProjectDetailsIntent, ProjectDetailsEvent>(ProjectDetailsState.Loading) {

    private val id = savedState.get<String>(KEY_ID)

    init {
        onReload()
    }

    private fun onReload() {
        setState(ProjectDetailsState.Loading)
        viewModelScope.launch {
            useCase.getProject(id)
                .onSuccess { result ->
                    setState(ProjectDetailsState.Main(result.toProject()))
                }
                .onFailure { failure ->
                    when (failure) {
                        is AppFailure.UnknownFailure -> setState(ProjectDetailsState.Error)
                        else -> setState(ProjectDetailsState.Error)
                    }
                }
        }
    }

    override fun handleIntent(intent: ProjectDetailsIntent) {
        when (intent) {
            ProjectDetailsIntent.OnClickReload -> onReload()
        }
    }
}