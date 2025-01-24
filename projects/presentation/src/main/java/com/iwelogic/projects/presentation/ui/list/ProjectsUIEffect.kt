package com.iwelogic.projects.presentation.ui.list

sealed class ProjectsUIEffect {
    data object OpenProjectDetails : ProjectsUIEffect()
}