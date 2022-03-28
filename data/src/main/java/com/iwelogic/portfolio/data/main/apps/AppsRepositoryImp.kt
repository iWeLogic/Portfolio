package com.iwelogic.portfolio.presentation.main.apps

import com.iwelogic.portfolio.presentation.source.DataSource
import com.iwelogic.portfolio.domain.main.apps.AppsRepository
import com.iwelogic.portfolio.domain.models.App
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AppsRepositoryImp(private val dataSource: DataSource) : AppsRepository {

    override fun getApps(): Flow<Result<List<App>>> {
        return flow {
            emit(Result.Loading)
            delay(500)
            emit(dataSource.getApps())
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }
}