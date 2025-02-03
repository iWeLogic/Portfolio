package com.iwelogic.settings.presentation

import android.content.*
import android.content.pm.*
import androidx.appcompat.app.*
import com.iwelogic.core_ui.*
import com.iwelogic.core_ui.base.*
import com.iwelogic.settings.presentation.models.*
import dagger.hilt.android.lifecycle.*
import dagger.hilt.android.qualifiers.*
import java.util.*
import javax.inject.*


@HiltViewModel
class SettingsViewModel @Inject constructor(@ApplicationContext private val context: Context, private val themeHolder: ThemeHolder) :
    BaseViewModel<SettingsState, SettingsUIEffect, SettingsEvent>(SettingsState(isDarkTheme = themeHolder.isDark)) {
    private var packageName: String = ""

    init {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageName = context.packageName
            setState(state.value.copy(versionName = pInfo.versionName))
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun obtainEvent(userEvent: SettingsEvent) {
        when (userEvent) {
            is SettingsEvent.OnClickChangeTheme -> {
                themeHolder.isDark = userEvent.isDark
                setState(state.value.copy(isDarkTheme = userEvent.isDark))
            }
            is SettingsEvent.OnClickChangeLanguage -> {
                val language = Language.entries.firstOrNull { it.index == userEvent.index } ?: Language.EN
                setState(state.value.copy(language = language))
                sendUiEffect(SettingsUIEffect.ChangeLanguage(language.code))
            }
            is SettingsEvent.OnClickChangeNotificationStatus -> {
                setState(state.value.copy(isNotificationOn = userEvent.isOn))
            }
            SettingsEvent.CheckLanguage -> {
                setState(state.value.copy(language = Language.entries.firstOrNull { it.code == getCurrentLocale(context).language } ?: Language.EN))
            }
            SettingsEvent.OnClickRate -> sendUiEffect(SettingsUIEffect.RateApp(packageName = packageName))
            SettingsEvent.OnClickShare -> sendUiEffect(SettingsUIEffect.ShareApp(packageName = packageName))
            SettingsEvent.OnClickSupport -> sendUiEffect(SettingsUIEffect.SendEmail(email = "novaknazar@gmail.com"))
        }
    }

    private fun getCurrentLocale(context: Context): Locale {
        val locales = AppCompatDelegate.getApplicationLocales()
        return locales.get(0) ?: context.resources.configuration.locales.get(0)
    }
}

