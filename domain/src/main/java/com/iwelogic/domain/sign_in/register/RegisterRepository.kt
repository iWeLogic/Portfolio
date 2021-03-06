package com.iwelogic.domain.sign_in.register

import com.iwelogic.domain.models.RegisterDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    fun register(data: RegisterDomain): Flow<Result<Any>>
}

