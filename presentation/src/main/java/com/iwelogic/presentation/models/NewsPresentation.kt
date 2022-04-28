package com.iwelogic.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsPresentation(

    val id: Int? = null,
    var type: CellType = CellType.SIMPLE,
    val title: String? = null,
    val description: String? = null,
    val image: String? = null

) : Parcelable {
    companion object {
        fun getProgressItem() = NewsPresentation().apply { type = CellType.PROGRESS }
    }
}