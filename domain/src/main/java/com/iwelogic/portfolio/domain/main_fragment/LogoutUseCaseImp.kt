package com.iwelogic.portfolio.domain.main_fragment

import com.iwelogic.portfolio.data.local_user.LocalUserRepository
import com.iwelogic.portfolio.data.models.Result
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