package com.iwelogic.profile.data.repository

import com.iwelogic.profile.data.datasource.*
import com.iwelogic.profile.data.mapper.*
import com.iwelogic.profile.domain.models.*
import com.iwelogic.profile.domain.repository.*

class ProfileRepositoryImp(private val profileDataSource: ProfileDataSource) : ProfileRepository {

    override suspend fun getProfile(): Result<ProfileDomain> {
        return profileDataSource.getProfile().map { it.toProfileDomain() }
    }

    override suspend fun getContacts(): Result<List<ContactDomain>> {
        return profileDataSource.getContacts().map { result -> result.map { it.toContactDomain() } }
    }

    override suspend fun getStudies(): Result<List<StudyDomain>> {
        return profileDataSource.getStudies().map { result -> result.map { it.toStudyDomain() } }
    }

    override suspend fun getJobs(): Result<List<JobDomain>> {
        return profileDataSource.getJobs().map { result -> result.map { it.toJobDomain() } }
    }
}