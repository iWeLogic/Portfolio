package com.iwelogic.domain.sign_in.register

import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun register(email: String?, firstName: String?, lastName: String?, passwordOne: String?, passwordTwo: String?): Flow<Result<Any>>
}