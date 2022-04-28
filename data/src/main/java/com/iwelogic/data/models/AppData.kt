package com.iwelogic.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AppData(

    @field:SerializedName("id")
    @Expose
    val id: Int? = null,

    @field:SerializedName("title")
    @Expose
    val title: String? = null,

    @field:SerializedName("description")
    @Expose
    val description: String? = null,

    @field:SerializedName("icon")
    @Expose
    val icon: String? = null,

    @field:SerializedName("images")
    @Expose
    val images: List<String>? = null,

    @field:SerializedName("url")
    @Expose
    val url: String? = null,

    @field:SerializedName("releaseDate")
    @Expose
    val releaseDate: Long? = null,

    @field:SerializedName("spendHours")
    @Expose
    val spendHours: Int? = null,

    @field:SerializedName("tags")
    @Expose
    val tags: List<String>? = null
)
