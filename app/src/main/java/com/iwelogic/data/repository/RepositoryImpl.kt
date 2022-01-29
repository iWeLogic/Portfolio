package com.iwelogic.data.repository

import com.iwelogic.data.Result
import com.iwelogic.data.source.DataSource
import com.iwelogic.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl constructor(private val dataSource: DataSource) : Repository {

    override suspend fun register(email: String, password: String): Flow<Result<User>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.register(email, password))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }
}