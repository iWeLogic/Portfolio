package com.iwelogic.projects.data.datasource

import com.iwelogic.core.base.datasource.*
import com.iwelogic.projects.data.dto.*
import com.iwelogic.projects.data.remote.*
import javax.inject.Inject

class ProjectsDataSource @Inject constructor(private val api: ProjectsApi) : BaseDataSource() {

    suspend fun getProjects(): Result<List<ProjectDto>> {
        return getResponse(request = { api.getProjects() })
    }
}