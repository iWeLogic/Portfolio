package com.iwelogic.portfolio.data.source

import com.iwelogic.portfolio.domain.main.models.Result
import com.iwelogic.portfolio.domain.main.models.RegisterData
import com.iwelogic.portfolio.domain.main.models.SignInData
import com.iwelogic.portfolio.domain.main.models.User

interface DataSource {

    suspend fun register(data: RegisterData): Result<User>

    suspend fun login(data: SignInData): Result<User>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>
}