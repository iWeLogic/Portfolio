package com.iwelogic.domain.main.apps

import com.iwelogic.domain.models.AppDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface AppsUseCase {
    fun getApps(): Flow<Result<List<AppDomain>>>
}