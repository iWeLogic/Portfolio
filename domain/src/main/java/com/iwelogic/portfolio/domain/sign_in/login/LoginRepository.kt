package com.iwelogic.portfolio.domain.sign_in.login

import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.models.DomainSignIn
import com.iwelogic.portfolio.domain.models.DomainUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(data: DomainSignIn): Flow<Result<DomainUser>>

    suspend fun resendConfirmation(login: String?)
}