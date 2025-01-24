package com.iwelogic.projects.domain.repository

import com.iwelogic.projects.domain.models.ProjectDomain

interface ProjectsRepository {
    suspend fun getProjects(): Result<List<ProjectDomain>>
}