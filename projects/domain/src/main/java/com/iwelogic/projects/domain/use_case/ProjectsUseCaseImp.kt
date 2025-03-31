package com.iwelogic.projects.domain.use_case

import com.iwelogic.projects.domain.models.ProjectDomain
import com.iwelogic.projects.domain.repository.ProjectsRepository
import kotlinx.coroutines.*

class ProjectsUseCaseImp(private val repository: ProjectsRepository) : ProjectsUseCase {

    private var cacheProjects = listOf<ProjectDomain>()

    override suspend fun getProjects(): Result<List<ProjectDomain>> = withContext(Dispatchers.IO) {
        val result = repository.getProjects()
        if (result.isSuccess) {
            val temp = (result.getOrNull() ?: listOf()).toMutableList()
            temp.sortBy { it.id }
            cacheProjects = temp
            Result.success(cacheProjects)
        } else {
            result
        }
    }

    override suspend fun getProject(id: String?, isForceReload: Boolean): Result<ProjectDomain> = withContext(Dispatchers.IO) {
        var project = cacheProjects.firstOrNull { it.id == id }
        if (project != null && !isForceReload) {
            Result.success(project)
        } else {
            val result = repository.getProjects()
            if (result.isSuccess) {
                cacheProjects = result.getOrNull() ?: listOf()
                project = cacheProjects.firstOrNull { it.id == id }
                if (project != null) {
                    Result.success(project)
                } else {
                    Result.failure(Throwable(message = "Project didn't found"))
                }
            } else {
                Result.failure(result.exceptionOrNull()!!)
            }
        }
    }
}

