package com.iwelogic.projects.data.repository

import com.iwelogic.projects.data.datasource.*
import com.iwelogic.projects.data.mapper.*
import com.iwelogic.projects.domain.models.*
import com.iwelogic.projects.domain.repository.*

class ProjectsRepositoryImp(private val projectsDataSource: ProjectsDataSource) : ProjectsRepository {

    override suspend fun getProjects(): Result<List<ProjectDomain>> {
        return projectsDataSource.getProjects().map { result ->
            result.map { it.toProject() }
        }
    }
}