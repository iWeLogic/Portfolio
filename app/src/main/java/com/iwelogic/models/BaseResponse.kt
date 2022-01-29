package com.iwelogic.models

import com.google.gson.annotations.SerializedName
import com.iwelogic.data.Result

open class BaseResponse(

    @field:SerializedName("code")
    val code: Result.Error.Code? = null,

    @field:SerializedName("message")
    val message: String? = null
)
