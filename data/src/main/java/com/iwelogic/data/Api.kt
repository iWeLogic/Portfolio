package com.iwelogic.data

import com.iwelogic.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/api/users/register")
    suspend fun register(@Body registerData: RegisterData): Response<Any>

    @PUT("api/data/Users")
    suspend fun updateUser(@Body userData: UserData): Response<UserData>

    @GET("api/data/Users")
    suspend fun getUser(@Query("where") where: String): Response<List<UserData>>

    @GET("api/data/feedbacks")
    suspend fun getFeedbacks(@Query("pageSize") pageSize: Int, @Query("offset") offset: Int, @Query("sortBy") sortBy: String = "`created` desc"): Response<List<FeedbackData>>

    @POST("api/data/feedbacks")
    suspend fun addFeedback(@Body data: FeedbackData): Response<FeedbackData>

    @GET("/api/users/restorepassword/{email}")
    suspend fun remember(@Path("email") email: String?): Response<Void>

    @POST("/api/users/login")
    suspend fun login(@Body registerData: SignInData): Response<UserData>

    @POST("/api/users/resendconfirmation/{email}")
    suspend fun resendEmailConfirmation(@Path("email") email: String?): Response<Void>

    @GET("/api/data/apps")
    suspend fun getApps(@Query("sortBy") sortBy: String = "`id` desc", @Query("pageSize") pageSize: String = "100"): Response<List<AppData>>

    @GET("/api/data/news")
    suspend fun getNews(@Query("pageSize") pageSize: Int, @Query("offset") offset: Int, @Query("sortBy") sortBy: String = "id"): Response<List<NewsData>>

    @GET("/api/services/data/info")
    suspend fun getInfo(): Response<InfoData>

}