package com.iwelogic.portfolio.domain.sign_in.register

import com.iwelogic.portfolio.domain.models.DomainRegister
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun register(data: DomainRegister): Flow<Result<Any>>
}