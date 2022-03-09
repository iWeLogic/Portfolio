package com.iwelogic.portfolio.domain.main_activity

import com.iwelogic.portfolio.domain.LocalUserRepository
import com.iwelogic.portfolio.domain.models.ExistStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class UserExistUseCaseImp(var localUserRepository: LocalUserRepository) : UserExistUseCase {

    override suspend fun checkIsUserExist(): ExistStatus {
        return localUserRepository.userFlow.map {
            if (it.userToken.isNullOrEmpty()) ExistStatus.False else ExistStatus.True
        }.flowOn(Dispatchers.IO).first()
    }
}