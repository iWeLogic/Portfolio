package com.iwelogic.data.sign_in.login

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.DataSignIn
import com.iwelogic.data.models.DataUser
import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.models.DomainSignIn
import com.iwelogic.domain.models.DomainUser
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.sign_in.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LoginRepositoryImp(private val dataSource: DataSource, private val mapper: Mapper<DataUser, DomainUser>) : LoginRepository {

    override fun login(data: DomainSignIn): Flow<Result<DomainUser>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.login(DataSignIn(data.login, data.password)))
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(mapper.map(result.data ?: DataUser() ))
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Finish
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun resendConfirmation(login: String?) {
        dataSource.resendEmailConfirmation(login)
    }
}