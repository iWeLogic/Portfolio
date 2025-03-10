package com.iwelogic.projects.presentation.ui.details

sealed class ProjectDetailsIntent {
    data object OnClickReload : ProjectDetailsIntent()
}