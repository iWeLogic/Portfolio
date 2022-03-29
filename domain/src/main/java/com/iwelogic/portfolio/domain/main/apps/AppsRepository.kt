package com.iwelogic.portfolio.domain.main.apps

import com.iwelogic.portfolio.domain.models.DomainApp
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface AppsRepository {
    fun getApps(): Flow<Result<List<DomainApp>>>
}