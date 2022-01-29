package com.iwelogic.data

import com.iwelogic.models.RegisterUser
import com.iwelogic.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/api/users/register")
    suspend fun register(@Body registerUser: RegisterUser): Response<User>
}