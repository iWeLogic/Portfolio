package com.iwelogic.data

import com.iwelogic.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/api/users/register")
    suspend fun register(@Body registerData: RegisterData): Response<Any>

    @GET("/api/users/restorepassword/{email}")
    suspend fun remember(@Path("email") email: String?): Response<Void>

    @POST("/api/users/login")
    suspend fun login(@Body registerData: SignInData): Response<UserData>

    @POST("/api/users/resendconfirmation/{email}")
    suspend fun resendEmailConfirmation(@Path("email") email: String?): Response<Void>

    @GET("/api/data/apps")
    suspend fun getApps(@Query("sortBy") sortBy: String = "id", @Query("pageSize") pageSize: String = "100"): Response<List<AppData>>

    @GET("/api/data/news")
    suspend fun getNews(@Query("pageSize") pageSize: Int, @Query("offset") offset: Int, @Query("sortBy") sortBy: String = "id"): Response<List<NewsData>>

    @GET("/api/services/data/info")
    suspend fun getInfo(): Response<InfoData>

}