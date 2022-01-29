package com.iwelogic.models

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("userStatus")
    val userStatus: UserStatus? = null,

    @field:SerializedName("name")
    val name: Any? = null,

    @field:SerializedName("objectId")
    val objectId: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) {
    enum class UserStatus {

        EMAIL_CONFIRMATION_PENDING
    }
}
