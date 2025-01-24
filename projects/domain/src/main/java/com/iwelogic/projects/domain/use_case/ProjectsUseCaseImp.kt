package com.iwelogic.projects.domain.use_case

import com.iwelogic.projects.domain.models.ProjectDomain
import com.iwelogic.projects.domain.repository.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectsUseCaseImp(private val repository: ProjectsRepository) : ProjectsUseCase {

    override suspend fun getProjects(): Result<List<ProjectDomain>> = withContext(Dispatchers.IO) {
        repository.getProjects()
    }
}

