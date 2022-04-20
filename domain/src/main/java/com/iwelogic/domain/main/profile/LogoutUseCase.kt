package com.iwelogic.domain.main.profile

import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface LogoutUseCase {

    fun logout(): Flow<Result<Any>>
}