package com.iwelogic.domain.sign_in.forgot_password

import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface ForgotPasswordRepository {

    fun remember(email: String): Flow<Result<Void>>
}