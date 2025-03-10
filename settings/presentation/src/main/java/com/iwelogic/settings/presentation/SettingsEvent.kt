package com.iwelogic.settings.presentation

sealed class SettingsEvent {
    data class ChangeLanguage(val code: String) : SettingsEvent()
    data class SendEmail(val email: String) : SettingsEvent()
    data class ShareApp(val packageName: String) : SettingsEvent()
    data class RateApp(val packageName: String) : SettingsEvent()
}