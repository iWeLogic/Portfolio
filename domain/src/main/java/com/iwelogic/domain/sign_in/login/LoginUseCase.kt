package com.iwelogic.domain.sign_in.login

import com.iwelogic.domain.models.Result
import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun login(email: String?, password: String?): Flow<Result<UserDomain>>
}