package com.iwelogic.portfolio.domain.models

import com.google.gson.annotations.SerializedName

data class News(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("image")
    val image: String? = null,
) {
    companion object {
        const val LOADING = -1

        fun getLoadingItem() = News(id = LOADING)
    }
}
