package com.iwelogic.projects.data.dto

import com.google.gson.annotations.SerializedName

data class ProjectDto(

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("downloads")
	val downloads: String,

	@field:SerializedName("tags")
	val tags: List<String>
)