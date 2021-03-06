package com.iwelogic.data.main.apps

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.AppData
import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.apps.AppsRepository
import com.iwelogic.domain.models.AppDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class AppsRepositoryImp(private val dataSource: DataSource, private val mapper: Mapper<AppData, AppDomain>) : AppsRepository {

    override fun getApps(): Flow<Result<List<AppDomain>>> {
        return flow {
            emit(Result.Loading)
            delay(200)
            emit(dataSource.getApps())
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.map { app -> mapper.map(app) })
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Finish
            }
        }.flowOn(Dispatchers.IO)
    }
}