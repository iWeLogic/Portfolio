package com.iwelogic.projects.presentation.ui.list

sealed class ProjectsIntent {
    data class OpenDetails(val id: String) : ProjectsIntent()
    data object OnClickReload : ProjectsIntent()
}