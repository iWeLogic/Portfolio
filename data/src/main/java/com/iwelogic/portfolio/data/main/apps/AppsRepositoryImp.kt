package com.iwelogic.portfolio.data.main.apps

import com.iwelogic.portfolio.core.Mapper
import com.iwelogic.portfolio.data.models.DataApp
import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.main.apps.AppsRepository
import com.iwelogic.portfolio.domain.models.DomainApp
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class AppsRepositoryImp(private val dataSource: DataSource, private val mapper: Mapper<DataApp, DomainApp>) : AppsRepository {

    override fun getApps(): Flow<Result<List<DomainApp>>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.getApps())
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.map { news -> mapper.map(news) })
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Loading
            }
        }.flowOn(Dispatchers.IO)
    }
}