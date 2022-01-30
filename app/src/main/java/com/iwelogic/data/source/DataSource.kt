package com.iwelogic.data.source

import com.iwelogic.data.Result
import com.iwelogic.models.RegisterData
import com.iwelogic.models.SignInData
import com.iwelogic.models.User

interface DataSource {

    suspend fun register(data: RegisterData): Result<User>

    suspend fun login(data: SignInData): Result<User>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>
}