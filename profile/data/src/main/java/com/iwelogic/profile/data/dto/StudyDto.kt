package com.iwelogic.profile.data.dto

import com.google.gson.annotations.SerializedName

data class StudyDto(

	@field:SerializedName("duration")
	val duration: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
