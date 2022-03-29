package com.iwelogic.portfolio.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class News(

    var type: CellType = CellType.SIMPLE,

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

)  {
    companion object {
        fun getProgressItem() = News().apply { type = CellType.PROGRESS }
    }
}