package com.iwelogic.settings.presentation

import com.iwelogic.settings.presentation.models.*

data class SettingsState(
    val versionName: String? = null,
    val isDarkTheme: Boolean = true,
    val language: Language = Language.EN,
    val isNotificationOn: Boolean = false
)