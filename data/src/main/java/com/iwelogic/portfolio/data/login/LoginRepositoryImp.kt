package com.iwelogic.portfolio.data.login


import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.login.LoginRepository
import com.iwelogic.portfolio.domain.models.SignInData
import com.iwelogic.portfolio.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.iwelogic.portfolio.domain.models.Result

class LoginRepositoryImp(private val dataSource: DataSource) : LoginRepository {

    override fun login(data: SignInData): Flow<Result<User>> {
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