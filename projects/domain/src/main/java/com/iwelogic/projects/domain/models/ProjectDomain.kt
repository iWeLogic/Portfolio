package com.iwelogic.projects.domain.models

data class ProjectDomain(
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