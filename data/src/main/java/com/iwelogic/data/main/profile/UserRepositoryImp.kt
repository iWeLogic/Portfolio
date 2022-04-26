package com.iwelogic.data.main.profile

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.UserData
import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.profile.UserRepository
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class UserRepositoryImp(private val dataSource: DataSource, private val mapper: Mapper<UserData, UserDomain>) : UserRepository {

    override fun update(userData: UserDomain): Flow<Result<UserDomain>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.updateUser(mapper.reverseMap(userData)))
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.let { mapper.map(it) })
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Finish
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun get(objectId: String?): Flow<Result<UserDomain>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.getUser(objectId))
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.firstOrNull()?.let { mapper.map(it) })
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Finish
            }
        }.flowOn(Dispatchers.IO)
    }
}