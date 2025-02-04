package com.iwelogic.projects.presentation.ui.details

sealed class ProjectDetailsEvent {
    data object OnClickReload : ProjectDetailsEvent()
}