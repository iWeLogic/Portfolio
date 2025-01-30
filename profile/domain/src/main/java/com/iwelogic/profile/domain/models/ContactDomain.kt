package com.iwelogic.profile.domain.models

data class ContactDomain(
    val name: String,
    val link: String? = null,
    val id: Int,
    val type: String,
    val value: String
)