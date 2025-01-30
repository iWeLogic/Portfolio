package com.iwelogic.projects.presentation.ui.list

sealed class ProjectsUserEvent {
    data class OpenDetails(val id: String) : ProjectsUserEvent()
    data object OnClickReload : ProjectsUserEvent()
}