package com.iwelogic.portfolio.data.source

import com.iwelogic.portfolio.domain.models.*

interface DataSource {

    suspend fun register(data: RegisterData): Result<User>

    suspend fun login(data: SignInData): Result<User>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>

    suspend fun getNews(pageSize: Int, offset: Int): Result<List<News>>

    suspend fun getApps(): Result<List<App>>
}