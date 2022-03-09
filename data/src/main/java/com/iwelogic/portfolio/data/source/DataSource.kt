package com.iwelogic.portfolio.data.source

import com.iwelogic.portfolio.domain.models.RegisterData
import com.iwelogic.portfolio.domain.models.SignInData
import com.iwelogic.portfolio.domain.models.User
import com.iwelogic.portfolio.domain.models.Result

interface DataSource {

    suspend fun register(data: RegisterData): Result<User>

    suspend fun login(data: SignInData): Result<User>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>
}