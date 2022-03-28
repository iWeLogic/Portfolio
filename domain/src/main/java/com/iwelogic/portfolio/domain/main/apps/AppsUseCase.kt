package com.iwelogic.portfolio.domain.main.apps

import com.iwelogic.portfolio.domain.models.App
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface AppsUseCase {
    fun getApps(): Flow<Result<List<App>>>
}