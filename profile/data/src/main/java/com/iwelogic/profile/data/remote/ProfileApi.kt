package com.iwelogic.profile.data.remote

import com.iwelogic.profile.data.dto.*
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {

    @GET("profile")
    suspend fun getProfile(): Response<ProfileDto>

    @GET("contacts")
    suspend fun getContacts(): Response<List<ContactDto>>

    @GET("studyHistory")
    suspend fun getStudies(): Response<List<StudyDto>>

    @GET("jobHistory")
    suspend fun getJobs(): Response<List<JobDto>>
}