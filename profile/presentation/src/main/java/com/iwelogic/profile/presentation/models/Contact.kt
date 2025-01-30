package com.iwelogic.profile.presentation.models

data class Contact(
    val name: String,
    val link: String? = null,
    val id: Int,
    val type: String,
    val value: String
)
