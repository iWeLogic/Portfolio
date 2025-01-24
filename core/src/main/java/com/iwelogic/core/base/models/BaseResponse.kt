package com.iwelogic.core.base.models

import com.google.gson.annotations.SerializedName

open class BaseResponse(

    @field:SerializedName("message")
    val message: String? = null
)