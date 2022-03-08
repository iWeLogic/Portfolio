package com.iwelogic.portfolio.domain.main.login

import com.iwelogic.portfolio.domain.main.models.Result
import com.iwelogic.portfolio.domain.main.models.SignInData
import com.iwelogic.portfolio.domain.main.models.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(data: SignInData): Flow<Result<User>>
}