package com.iwelogic.settings.presentation


sealed class SettingsIntent {
    class OnClickChangeTheme(val isDark: Boolean) : SettingsIntent()
    class OnClickChangeNotificationStatus(val isOn: Boolean) : SettingsIntent()
    class OnClickChangeLanguage(val index: Int) : SettingsIntent()
    data object OnClickSupport : SettingsIntent()
    data object OnClickShare : SettingsIntent()
    data object OnClickRate : SettingsIntent()
    data object CheckLanguage : SettingsIntent()
}