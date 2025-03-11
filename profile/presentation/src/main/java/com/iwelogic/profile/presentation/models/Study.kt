package com.iwelogic.profile.presentation.models

data class Study(
	val duration: String,
	val name: String,
	val id: Int
) {
	companion object {
		val preview = Study("2004-2009", "Bachelor, TNTU (Computer science",  1)
	}
}
