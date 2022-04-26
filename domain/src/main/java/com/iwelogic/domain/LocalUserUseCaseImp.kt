package com.iwelogic.domain

import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

class LocalUserUseCaseImp(var localUserRepository: LocalUserRepository) : LocalUserUseCase {

    override suspend fun getUser(): Flow<UserDomain> {
        return localUserRepository.userFlow
    }
}