package com.iwelogic.domain.models

data class DomainApp(

    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val icon: String? = null,
    val images: List<String>? = null,
    val url: String? = null,
    val releaseDate: Long? = null,
    val spendHours: Int? = null,
    val tags: List<String>? = null
)
