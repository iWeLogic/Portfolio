package com.iwelogic.portfolio.presentation.sign_in.register

import com.iwelogic.portfolio.domain.models.RegisterData
import com.iwelogic.portfolio.presentation.source.DataSource
import com.iwelogic.portfolio.domain.sign_in.register.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.flowOn

class RegisterRepositoryImp constructor(private val dataSource: DataSource) : RegisterRepository {

    override fun register(data: RegisterData): Flow<Result<Any>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.register(data))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }
}