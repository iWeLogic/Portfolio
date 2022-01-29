package com.iwelogic.models

import com.google.gson.annotations.SerializedName

data class RegisterUser(

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)
