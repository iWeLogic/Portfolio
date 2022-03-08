package com.iwelogic.portfolio.domain.main.models

import com.google.gson.annotations.SerializedName

open class BaseResponse(

    @field:SerializedName("code")
    val code: Result.Error.Code? = null,

    @field:SerializedName("message")
    val message: String? = null
)
