package com.iwelogic.portfolio.data.login

import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.login.LoginRepository
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.models.SignInData
import com.iwelogic.portfolio.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepositoryImp(private val dataSource: DataSource) : LoginRepository {

    override fun login(data: SignInData): Flow<Result<User>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.login(data))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun resendConfirmation(login: String?) {
        dataSource.resendEmailConfirmation(login)
    }
}