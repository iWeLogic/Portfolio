package com.iwelogic.portfolio.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(

    var type: CellType = CellType.SIMPLE,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("image")
    val image: String? = null

) : Parcelable {
    companion object {
        fun getProgressItem() = News().apply { type = CellType.PROGRESS }
    }
}