package com.iwelogic.portfolio.domain.main.models

import com.google.gson.annotations.SerializedName

data class SignInData(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)
