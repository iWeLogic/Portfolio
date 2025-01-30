package com.iwelogic.profile.data.dto

import com.google.gson.annotations.SerializedName

data class ContactDto(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("value")
	val value: String
)
