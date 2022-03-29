package com.iwelogic.portfolio.data.sign_in.login

import com.iwelogic.portfolio.data.models.DataSignIn
import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.models.DomainSignIn
import com.iwelogic.portfolio.domain.models.DomainUser
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.sign_in.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepositoryImp(private val dataSource: DataSource) : LoginRepository {

    override fun login(data: DomainSignIn): Flow<Result<DomainUser>> {
        return flow {
            emit(Result.Loading)
            val res = dataSource.login(DataSignIn(data.login, data.password))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun resendConfirmation(login: String?) {
        dataSource.resendEmailConfirmation(login)
    }
}