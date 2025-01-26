package com.iwelogic.profile.data.repository

import com.iwelogic.profile.data.datasource.*
import com.iwelogic.profile.data.mapper.*
import com.iwelogic.profile.domain.models.*
import com.iwelogic.profile.domain.repository.*

class ProfileRepositoryImp(private val profileDataSource: ProfileDataSource) : ProfileRepository {

    override suspend fun getProfile(): Result<ProfileDomain> {
        return profileDataSource.getProfile().map { it.toProfile() }
    }
}