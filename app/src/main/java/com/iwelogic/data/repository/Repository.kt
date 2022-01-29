package com.iwelogic.data.repository

import com.iwelogic.data.Result
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun register(email: String, password: String): Flow<Result<Any>>
}