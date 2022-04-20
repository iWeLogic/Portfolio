package com.iwelogic.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("name")
	@Expose
	val name: String? = null,

	@field:SerializedName("user-token")
	@Expose
	val userToken: String? = null,

	@field:SerializedName("email")
	@Expose
	val email: String? = null
)
