package com.iwelogic.portfolio.domain.profile

import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface LogoutUseCase {

    fun logout(): Flow<Result<Any>>
}