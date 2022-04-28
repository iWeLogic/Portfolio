package com.iwelogic.data.main.info

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.InfoData
import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.info.InfoRepository
import com.iwelogic.domain.models.InfoDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class InfoRepositoryImp(private val dataSource: DataSource, private val mapper: Mapper<InfoData, InfoDomain>) : InfoRepository {

    override fun getInfo(): Flow<Result<InfoDomain>> {
        return flow {
            emit(Result.Loading)
            delay(200)
            emit(dataSource.getInfo())
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
}