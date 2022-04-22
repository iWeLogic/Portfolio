package com.iwelogic.domain.sign_in.forgot_password

import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface ForgotPasswordUseCase {

    fun remember(email: String?): Flow<Result<Any>>
}