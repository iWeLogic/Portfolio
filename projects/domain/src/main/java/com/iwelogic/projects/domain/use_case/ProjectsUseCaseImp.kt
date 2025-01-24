package com.iwelogic.projects.domain.use_case

import com.iwelogic.projects.domain.models.ProjectDomain
import com.iwelogic.projects.domain.repository.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectsUseCaseImp(private val repository: ProjectsRepository) : ProjectsUseCase {

    private var cacheProjects = listOf<ProjectDomain>();

    override suspend fun getProjects(): Result<List<ProjectDomain>> = withContext(Dispatchers.IO) {
        val result = repository.getProjects()
        if (result.isSuccess) {
            cacheProjects = result.getOrNull() ?: listOf()
        }
        result
    }

    override suspend fun getProject(id: String?): Result<ProjectDomain> = withContext(Dispatchers.IO) {
        var project = cacheProjects.firstOrNull { it.id == id }
        if (project != null) {
            Result.success(project)
        } else {
            val result = repository.getProjects()
            if (result.isSuccess) {
                cacheProjects = result.getOrNull() ?: listOf()
                project = cacheProjects.firstOrNull { it.id == id }
                if (project != null) {
                    Result.success(project)
                } else {
                    Result.failure(Throwable(message = "Project didnt finded"))
                }
            } else {
                Result.failure(result.exceptionOrNull()!!)
            }
        }
    }
}

