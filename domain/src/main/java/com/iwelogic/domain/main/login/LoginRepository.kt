package com.iwelogic.domain.main.login

import com.iwelogic.domain.main.models.Result
import com.iwelogic.domain.main.models.SignInData
import com.iwelogic.domain.main.models.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(data: SignInData): Flow<Result<User>>
}