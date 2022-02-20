package com.iwelogic.data.source

import com.iwelogic.domain.main.models.Result
import com.iwelogic.domain.main.models.RegisterData
import com.iwelogic.domain.main.models.SignInData
import com.iwelogic.domain.main.models.User

interface DataSource {

    suspend fun register(data: RegisterData): Result<User>

    suspend fun login(data: SignInData): Result<User>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>
}