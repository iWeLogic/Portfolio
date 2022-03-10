package com.iwelogic.portfolio.data

import com.iwelogic.portfolio.domain.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/api/users/register")
    suspend fun register(@Body registerData: RegisterData): Response<User>

    @POST("/api/users/login")
    suspend fun login(@Body registerData: SignInData): Response<User>

    @POST("/api/users/resendconfirmation/{email}")
    suspend fun resendEmailConfirmation(@Path("email") email: String?): Response<Void>

    @GET("/api/data/apps")
    suspend fun getApps(): Response<List<App>>

    @GET("/api/data/news")
    suspend fun getNews(@Query("pageSize") pageSize: Int, @Query("offset") offset: Int): Response<List<News>>
}