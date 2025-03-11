package com.iwelogic.projects.domain.use_case

import com.iwelogic.projects.domain.models.ProjectDomain

interface ProjectsUseCase {

    suspend fun getProjects(): Result<List<ProjectDomain>>

    suspend fun getProject(id: String?, isForceReload: Boolean): Result<ProjectDomain>
}

