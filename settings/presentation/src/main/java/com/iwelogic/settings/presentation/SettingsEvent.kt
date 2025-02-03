package com.iwelogic.settings.presentation


sealed class SettingsEvent {
    class OnClickChangeTheme(val isDark: Boolean) : SettingsEvent()
    class OnClickChangeNotificationStatus(val isOn: Boolean) : SettingsEvent()
    class OnClickChangeLanguage(val index: Int) : SettingsEvent()
    data object OnClickSupport : SettingsEvent()
    data object OnClickShare : SettingsEvent()
    data object OnClickRate : SettingsEvent()
    data object CheckLanguage : SettingsEvent()
}