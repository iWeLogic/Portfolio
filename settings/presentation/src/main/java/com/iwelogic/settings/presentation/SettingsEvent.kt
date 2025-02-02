package com.iwelogic.settings.presentation

sealed class SettingsEvent {
   class  OnChangeTheme(val isDark: Boolean) : SettingsEvent()
}