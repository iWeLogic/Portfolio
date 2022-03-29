package com.iwelogic.portfolio.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse(

    @field:SerializedName("code")
    @Expose
    val code: Result.Error.Code? = null,

    @field:SerializedName("message")
    @Expose
    val message: String? = null
)
