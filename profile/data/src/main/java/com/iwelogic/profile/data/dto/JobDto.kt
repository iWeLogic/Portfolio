package com.iwelogic.profile.data.dto

import com.google.gson.annotations.SerializedName

data class JobDto(

	@field:SerializedName("duration")
	val duration: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("position")
	val position: String
)
