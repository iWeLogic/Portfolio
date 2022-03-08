package com.iwelogic.portfolio.data.login


import com.iwelogic.portfolio.data.models.Result
import com.iwelogic.portfolio.data.models.SignInData
import com.iwelogic.portfolio.data.models.User
import com.iwelogic.portfolio.data.source.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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