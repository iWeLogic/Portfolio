package com.iwelogic.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FeedbackData(

    @field:SerializedName("created")
    @Expose
    val created: Long? = null,

    @field:SerializedName("name")
    @Expose
    val name: String? = null,

    @field:SerializedName("rating")
    @Expose
    val rating: Int? = null,

    @field:SerializedName("text")
    @Expose
    val text: String? = null,

    @field:SerializedName("objectId")
    @Expose
    val objectId: String? = null
)
