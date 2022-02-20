package com.iwelogic.domain.main.local_user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserUseCase @Inject constructor(var localUserRepository: LocalUserRepository) {

    fun getExistStatus(): Flow<ExistStatus> {
        return localUserRepository.userFlow.map {
            if (it.userToken.isNullOrEmpty()) ExistStatus.False else ExistStatus.True
        }.flowOn(Dispatchers.IO)
    }
}

sealed class ExistStatus {

    object True : ExistStatus()

    object False : ExistStatus()
}