package com.iwelogic.profile.presentation.ui

sealed class ProfileEvent {
    data class OpenLink (val url: String) : ProfileEvent()
    data class DialPhone (val phone: String) : ProfileEvent()
}