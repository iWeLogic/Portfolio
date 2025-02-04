package com.iwelogic.profile.presentation.ui

sealed class ProfileUIEffect {
    data class OpenLink (val url: String) : ProfileUIEffect()
    data class DialPhone (val phone: String) : ProfileUIEffect()
}