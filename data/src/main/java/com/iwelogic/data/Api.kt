package com.iwelogic.data

import com.iwelogic.data.models.RegisterData
import com.iwelogic.data.models.SignInData
import com.iwelogic.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @POST("/api/users/register")
    suspend fun register(@Body registerData: RegisterData): Response<User>

    @POST("/api/users/login")
    suspend fun login(@Body registerData: SignInData): Response<User>

    @POST("/api/users/resendconfirmation/{email}")
    suspend fun resendEmailConfirmation(@Path("email") email: String?): Response<Void>
}