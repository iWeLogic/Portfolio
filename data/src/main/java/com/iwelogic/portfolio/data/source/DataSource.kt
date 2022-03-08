package com.iwelogic.portfolio.data.source

import com.iwelogic.portfolio.data.models.RegisterData
import com.iwelogic.portfolio.data.models.SignInData
import com.iwelogic.portfolio.data.models.User
import com.iwelogic.portfolio.data.models.Result

interface DataSource {

    suspend fun register(data: RegisterData): Result<User>

    suspend fun login(data: SignInData): Result<User>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>
}