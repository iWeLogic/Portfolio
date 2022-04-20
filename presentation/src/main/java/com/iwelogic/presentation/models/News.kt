package com.iwelogic.presentation.models

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