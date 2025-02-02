package com.iwelogic.settings.presentation

import com.iwelogic.core_ui.*
import com.iwelogic.core_ui.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.*

@HiltViewModel
class SettingsViewModel @Inject constructor(private val themeHolder: ThemeHolder) :
    BaseViewModel<SettingsState, SettingsUIEffect, SettingsEvent>(SettingsState(themeHolder.isDark)) {

    override fun obtainEvent(userEvent: SettingsEvent) {
      when(userEvent) {
          is SettingsEvent.OnChangeTheme -> {
              themeHolder.isDark = userEvent.isDark
              setState(SettingsState(isDarkTheme = userEvent.isDark))
          }
      }
    }
}