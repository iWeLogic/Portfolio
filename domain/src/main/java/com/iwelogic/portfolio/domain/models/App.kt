package com.iwelogic.portfolio.domain.models

import com.google.gson.annotations.SerializedName

data class App(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("icon")
    val icon: String? = null,
)
