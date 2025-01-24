package com.iwelogic.projects.presentation.ui.details

import com.iwelogic.projects.presentation.models.*

sealed class ProjectDetailsState {
    data object Loading : ProjectDetailsState()
    data object Error : ProjectDetailsState()
    data class Main(val project: Project) : ProjectDetailsState()
}