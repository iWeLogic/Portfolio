package com.iwelogic.domain.main.profile

import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LogoutUseCaseImp(var localUserRepository: LocalUserRepository) : LogoutUseCase {

    override fun logout(): Flow<Result<Nothing>> {
        return flow {
            emit(Result.Loading)
            localUserRepository.clearData()
            emit(Result.Success(null))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }
}