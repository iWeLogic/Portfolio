package com.iwelogic.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserData(

    @field:SerializedName("objectId")
    @Expose
    val objectId: String? = null,

    @field:SerializedName("firstName")
    @Expose
    val firstName: String? = null,

    @field:SerializedName("lastName")
    @Expose
    val lastName: String? = null,

    @field:SerializedName("image")
    @Expose
    val image: String? = null,

    @field:SerializedName("email")
    @Expose
    val email: String? = null,

    @field:SerializedName("user-token")
    @Expose
    val userToken: String? = null
)
