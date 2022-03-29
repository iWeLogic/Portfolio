package com.iwelogic.portfolio.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataNews(

    @field:SerializedName("id")
    @Expose
    val id: Int? = null,

    @field:SerializedName("title")
    @Expose
    val title: String? = null,

    @field:SerializedName("description")
    @Expose
    val description: String? = null,

    @field:SerializedName("image")
    @Expose
    val image: String? = null
)