package com.iwelogic.projects.presentation.ui.list

import com.iwelogic.projects.presentation.models.*

sealed class ProjectsState {
    data object Loading : ProjectsState()
    data object Error : ProjectsState()
    data class Main(val projects: List<Project>) : ProjectsState()
}