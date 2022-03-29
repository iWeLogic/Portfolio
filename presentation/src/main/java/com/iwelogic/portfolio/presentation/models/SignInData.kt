package com.iwelogic.portfolio.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignInData(

    @field:SerializedName("login")
    @Expose
    val login: String? = null,

    @field:SerializedName("password")
    @Expose
    val password: String? = null
)
