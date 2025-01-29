package com.iwelogic.projects.presentation.models

data class Project(
    val id: String,
    val images: List<String>,
    val link: String,
    val icon: String,
    val description: String,
    val name: String,
    val rating: String,
    val downloads: String,
    val tags: List<String>
)