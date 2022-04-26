package com.iwelogic.domain.sign_in.login

import com.iwelogic.domain.models.Result
import com.iwelogic.domain.models.SignInDomain
import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(data: SignInDomain): Flow<Result<UserDomain>>

    suspend fun resendConfirmation(login: String?)
}