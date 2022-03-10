package com.iwelogic.portfolio.domain.apps

import com.iwelogic.portfolio.domain.models.App
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface AppsRepository {
    fun getApps(): Flow<Result<List<App>>>
}