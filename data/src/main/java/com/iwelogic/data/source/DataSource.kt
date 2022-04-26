package com.iwelogic.data.source

import com.iwelogic.data.models.AppData
import com.iwelogic.data.models.NewsData
import com.iwelogic.data.models.RegisterData
import com.iwelogic.data.models.SignInData
import com.iwelogic.data.models.UserData
import com.iwelogic.domain.models.Result

interface DataSource {

    suspend fun register(data: RegisterData): Result<Any>

    suspend fun remember(email: String): Result<Void>

    suspend fun login(data: SignInData): Result<UserData>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>

    suspend fun getNews(pageSize: Int, offset: Int): Result<List<NewsData>>

    suspend fun getApps(): Result<List<AppData>>
}