package com.iwelogic.portfolio.data.repository

import com.iwelogic.portfolio.domain.main.models.Result
import com.iwelogic.portfolio.domain.main.models.RegisterData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun register(data: RegisterData): Flow<Result<Any>>
}