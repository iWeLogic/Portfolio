package com.iwelogic.domain.main.models

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("user-token")
	val userToken: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
