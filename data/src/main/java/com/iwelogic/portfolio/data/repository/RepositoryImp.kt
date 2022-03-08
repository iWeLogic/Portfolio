package com.iwelogic.portfolio.data.repository

import com.iwelogic.portfolio.domain.main.models.Result
import com.iwelogic.portfolio.domain.main.models.RegisterData
import com.iwelogic.portfolio.domain.main.models.User
import com.iwelogic.portfolio.data.source.DataSource
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
}