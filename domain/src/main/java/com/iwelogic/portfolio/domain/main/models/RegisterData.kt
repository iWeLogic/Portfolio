package com.iwelogic.portfolio.domain.main.models

import com.google.gson.annotations.SerializedName

data class RegisterData(

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)