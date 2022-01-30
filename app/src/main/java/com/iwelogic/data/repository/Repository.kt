package com.iwelogic.data.repository

import com.iwelogic.data.Result
import com.iwelogic.models.RegisterData
import com.iwelogic.models.SignInData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun register(data: RegisterData): Flow<Result<Any>>

    suspend fun login(data: SignInData): Flow<Result<Any>>
}