package com.iwelogic.portfolio.domain.login

import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.models.SignInData
import com.iwelogic.portfolio.domain.models.User
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun login(data: SignInData): Flow<Result<User>>
}