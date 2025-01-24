package com.iwelogic.projects.presentation.ui.details

sealed class ProjectDetailsUIEffect {
    data object OpenProjectDetails : ProjectDetailsUIEffect()
}