package com.iwelogic.profile.domain.use_case

import com.iwelogic.profile.domain.models.ProfileDomain
import com.iwelogic.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileUseCaseImp(private val repository: ProfileRepository) : ProfileUseCase {

    override suspend fun getProfile(): Result<ProfileDomain> = withContext(Dispatchers.IO) {
        repository.getProfile()
    }
}

