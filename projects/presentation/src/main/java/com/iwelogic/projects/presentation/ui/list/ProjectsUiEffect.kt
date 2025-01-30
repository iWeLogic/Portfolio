package com.iwelogic.projects.presentation.ui.list

sealed class ProjectsUiEffect {
    data  class OpenProjectDetails (val id: String): ProjectsUiEffect()
}