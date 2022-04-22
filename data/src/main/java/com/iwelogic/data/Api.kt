package com.iwelogic.presentation

import com.iwelogic.data.models.DataApp
import com.iwelogic.data.models.DataNews
import com.iwelogic.data.models.DataRegister
import com.iwelogic.data.models.DataSignIn
import com.iwelogic.data.models.DataUser
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/api/users/register")
    suspend fun register(@Body registerData: DataRegister): Response<Any>

    @GET("/api/users/restorepassword/{email}")
    suspend fun remember(@Path("email") email: String?): Response<Void>

    @POST("/api/users/login")
    suspend fun login(@Body registerData: DataSignIn): Response<DataUser>

    @POST("/api/users/resendconfirmation/{email}")
    suspend fun resendEmailConfirmation(@Path("email") email: String?): Response<Void>

    @GET("/api/data/apps")
    suspend fun getApps(): Response<List<DataApp>>

    @GET("/api/data/news")
    suspend fun getNews(@Query("pageSize") pageSize: Int, @Query("offset") offset: Int): Response<List<DataNews>>
}