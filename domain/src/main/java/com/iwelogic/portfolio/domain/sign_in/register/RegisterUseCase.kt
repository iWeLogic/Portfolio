package com.iwelogic.portfolio.domain.sign_in.register

import com.iwelogic.portfolio.domain.models.RegisterData
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun register(data: RegisterData): Flow<Result<Any>>
}