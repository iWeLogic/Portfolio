package com.iwelogic.projects.presentation.ui.list

sealed class ProjectsEvent {
    data  class OpenProjectDetails (val id: String): ProjectsEvent()
}