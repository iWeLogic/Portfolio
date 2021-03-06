package com.iwelogic.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignInDomain(

    @field:SerializedName("login")
    @Expose
    val login: String? = null,

    @field:SerializedName("password")
    @Expose
    val password: String? = null
)
