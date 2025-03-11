package com.iwelogic.profile.presentation.models

data class Job(
	val duration: String,
	val name: String,
	val link: String,
	val id: Int,
	val position: String
) {
	companion object {
		val preview = Job("APR 2023 - JANUARY 2025", "iAgentur", "1", 1, "Senior software engineer")
	}
}
