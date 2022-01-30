package com.iwelogic.data.repository

import com.iwelogic.data.Result
import com.iwelogic.data.source.DataSource
import com.iwelogic.models.RegisterData
import com.iwelogic.models.SignInData
import com.iwelogic.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImp constructor(private val dataSource: DataSource) : Repository {

    override suspend fun register(data: RegisterData): Flow<Result<User>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.register(data))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun login(data: SignInData): Flow<Result<User>> {
        return flow {
            emit(Result.Loading)
            val responseLogin = dataSource.login(data)
            if (responseLogin is Result.Error && responseLogin.code == Result.Error.Code.NOT_CONFIRMED) {
                val responseEmailConfirmation = dataSource.resendEmailConfirmation(data.login)
                if (responseEmailConfirmation is Result.Error) {
                    emit(responseEmailConfirmation)
                } else {
                    emit(responseLogin)
                }
            } else {
                emit(responseLogin)
            }
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }
}