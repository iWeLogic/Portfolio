package com.iwelogic.profile.data.datasource

import com.iwelogic.core.base.datasource.*
import com.iwelogic.profile.data.dto.*
import com.iwelogic.profile.data.remote.*
import javax.inject.Inject

class ProfileDataSource @Inject constructor(private val api: ProfileApi) : BaseDataSource() {

    suspend fun getProfile(): Result<ProfileDto> {
        return getResponse(request = { api.getProfile() })
    }

    suspend fun getContacts(): Result<List<ContactDto>> {
        return getResponse(request = { api.getContacts() })
    }

    suspend fun getStudies(): Result<List<StudyDto>> {
        return getResponse(request = { api.getStudies() })
    }

    suspend fun getJobs(): Result<List<JobDto>> {
        return getResponse(request = { api.getJobs() })
    }
}