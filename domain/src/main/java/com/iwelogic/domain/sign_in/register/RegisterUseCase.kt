package com.iwelogic.domain.sign_in.register

import com.iwelogic.domain.models.DomainRegister
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun register(data: DomainRegister): Flow<Result<Any>>
}