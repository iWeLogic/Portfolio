package com.iwelogic.profile.presentation.models

data class Contact(
    val name: String,
    val link: String? = null,
    val id: Int,
    val type: String,
    val value: String
) {
    companion object {
        val preview = Contact("Linkedin", "https://www.linkedin.com/in/nazar-novak/", 1, "link", "nazar-novak")
    }
}
