package com.iwelogic.data.repository

import com.iwelogic.domain.main.models.Result
import com.iwelogic.domain.main.models.RegisterData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun register(data: RegisterData): Flow<Result<Any>>
}