package com.iwelogic.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InfoData(

	@field:SerializedName("firstName")
	@Expose
	val firstName: String? = null,

	@field:SerializedName("lastName")
	@Expose
	val lastName: String? = null,

	@field:SerializedName("gitHub")
	@Expose
	val gitHub: String? = null,

	@field:SerializedName("nickName")
	@Expose
	val nickName: String? = null,

	@field:SerializedName("description")
	@Expose
	val description: String? = null,

	@field:SerializedName("avatar")
	@Expose
	val avatar: String? = null,

	@field:SerializedName("email")
	@Expose
	val email: String? = null,

	@field:SerializedName("phone")
	@Expose
	val phone: String? = null,

	@field:SerializedName("telegram")
	@Expose
	val telegram: String? = null,

	@field:SerializedName("skype")
	@Expose
	val skype: String? = null,

	@field:SerializedName("linkedin")
	@Expose
	val linkedin: String? = null,

	@field:SerializedName("upWork")
	@Expose
	val upWork: String? = null,

	@field:SerializedName("resume")
	@Expose
	val resume: String? = null
)
