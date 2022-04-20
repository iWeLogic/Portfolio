package com.iwelogic.domain.sign_in.login

import com.iwelogic.domain.models.Result
import com.iwelogic.domain.models.DomainSignIn
import com.iwelogic.domain.models.DomainUser
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun login(data: DomainSignIn): Flow<Result<DomainUser>>
}