package com.iwelogic.profile.data.dto

import com.google.gson.annotations.SerializedName

data class ProfileDto(

	@field:SerializedName("rate")
	val rate: String,

	@field:SerializedName("surname")
	val surname: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("about")
	val about: String,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("years")
	val years: String,

	@field:SerializedName("position")
	val position: String
)