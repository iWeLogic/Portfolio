package com.iwelogic.projects.data.remote

import com.iwelogic.projects.data.dto.*
import retrofit2.Response
import retrofit2.http.GET

interface ProjectsApi {

    @GET("projects")
    suspend fun getProjects(): Response<List<ProjectDto>>
}