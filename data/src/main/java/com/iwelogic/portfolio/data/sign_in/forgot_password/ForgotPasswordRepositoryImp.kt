package com.iwelogic.portfolio.presentation.sign_in.forgot_password

import com.iwelogic.portfolio.presentation.source.DataSource
import com.iwelogic.portfolio.domain.sign_in.forgot_password.ForgotPasswordRepository

class ForgotPasswordRepositoryImp constructor(private val dataSource: DataSource) : ForgotPasswordRepository {

/*    override fun register(data: RegisterData): Flow<Result<Any>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.register(data))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }*/
}