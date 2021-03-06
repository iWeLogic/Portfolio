package com.iwelogic.data.source

import com.iwelogic.data.models.*
import com.iwelogic.domain.models.Result

interface DataSource {

    suspend fun register(data: RegisterData): Result<Any>

    suspend fun updateUser(data: UserData): Result<UserData>

    suspend fun getUser(objectId: String?): Result<List<UserData>>

    suspend fun getFeedbacks(pageSize: Int, offset: Int): Result<List<FeedbackData>>

    suspend fun addFeedback(data: FeedbackData): Result<FeedbackData>

    suspend fun remember(email: String): Result<Void>

    suspend fun login(data: SignInData): Result<UserData>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>

    suspend fun getNews(pageSize: Int, offset: Int): Result<List<NewsData>>

    suspend fun getApps(): Result<List<AppData>>

    suspend fun getInfo(): Result<InfoData>
}