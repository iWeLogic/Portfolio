package com.iwelogic.portfolio.presentation.models

import com.iwelogic.portfolio.data.models.CellType

data class News(

    var type: CellType = CellType.SIMPLE,
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val image: String? = null
) {
    companion object {
        fun getProgressItem() = News().apply { type = CellType.PROGRESS }
    }
}