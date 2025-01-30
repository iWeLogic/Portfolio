package com.iwelogic.profile.domain.repository

import com.iwelogic.profile.domain.models.*

interface ProfileRepository {

    suspend fun getProfile(): Result<ProfileDomain>

    suspend fun getContacts(): Result<List<ContactDomain>>
}