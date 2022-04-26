package com.iwelogic.domain

import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

class UserUseCaseImp(var localUserRepository: LocalUserRepository) : UserUseCase {

    override suspend fun getUser(): Flow<UserDomain> {
        return localUserRepository.userFlow
    }
}