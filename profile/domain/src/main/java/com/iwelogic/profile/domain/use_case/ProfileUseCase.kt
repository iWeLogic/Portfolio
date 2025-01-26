package com.iwelogic.profile.domain.use_case

import com.iwelogic.profile.domain.models.ProfileDomain

interface ProfileUseCase {

    suspend fun getProfile(): Result<ProfileDomain>
}

