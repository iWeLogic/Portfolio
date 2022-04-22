package com.iwelogic.data.sign_in.forgot_password

import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.sign_in.forgot_password.ForgotPasswordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ForgotPasswordRepositoryImp constructor(private val dataSource: DataSource) : ForgotPasswordRepository {

    override fun remember(email: String): Flow<Result<Void>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.remember(email))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }
}