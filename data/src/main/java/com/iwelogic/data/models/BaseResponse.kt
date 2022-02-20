package com.iwelogic.data.models

import com.google.gson.annotations.SerializedName

open class BaseResponse(

    @field:SerializedName("code")
    val code: com.iwelogic.data.Result.Error.Code? = null,

    @field:SerializedName("message")
    val message: String? = null
)
