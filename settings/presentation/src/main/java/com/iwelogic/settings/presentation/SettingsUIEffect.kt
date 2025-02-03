package com.iwelogic.settings.presentation

sealed class SettingsUIEffect {
    data class ChangeLanguage(val code: String) : SettingsUIEffect()
    data class SendEmail(val email: String) : SettingsUIEffect()
    data class ShareApp(val packageName: String) : SettingsUIEffect()
    data class RateApp(val packageName: String) : SettingsUIEffect()
}