package com.iwelogic.data.repository

import com.iwelogic.data.Result
import com.iwelogic.data.models.RegisterData
import com.iwelogic.data.models.SignInData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun register(data: RegisterData): Flow<Result<Any>>

    suspend fun login(data: SignInData): Flow<Result<Any>>
}