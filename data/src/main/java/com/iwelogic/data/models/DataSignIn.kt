package com.iwelogic.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataSignIn(

    @field:SerializedName("login")
    @Expose
    val login: String? = null,

    @field:SerializedName("password")
    @Expose
    val password: String? = null
)
