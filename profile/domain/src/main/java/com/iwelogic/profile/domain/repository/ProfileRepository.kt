package com.iwelogic.profile.domain.repository

import com.iwelogic.profile.domain.models.ProfileDomain

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileDomain>
}