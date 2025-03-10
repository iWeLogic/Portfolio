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
    BaseViewModel<SettingsState, SettingsIntent, SettingsEvent>(SettingsState(isDarkTheme = themeHolder.isDark)) {
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

    override fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.OnClickChangeTheme -> {
                themeHolder.isDark = intent.isDark
                setState(state.value.copy(isDarkTheme = intent.isDark))
            }
            is SettingsIntent.OnClickChangeLanguage -> {
                val language = Language.entries.firstOrNull { it.index == intent.index } ?: Language.EN
                setState(state.value.copy(language = language))
                sendEvent(SettingsEvent.ChangeLanguage(language.code))
            }
            is SettingsIntent.OnClickChangeNotificationStatus -> {
                setState(state.value.copy(isNotificationOn = intent.isOn))
            }
            SettingsIntent.CheckLanguage -> {
                setState(state.value.copy(language = Language.entries.firstOrNull { it.code == getCurrentLocale(context).language } ?: Language.EN))
            }
            SettingsIntent.OnClickRate -> sendEvent(SettingsEvent.RateApp(packageName = packageName))
            SettingsIntent.OnClickShare -> sendEvent(SettingsEvent.ShareApp(packageName = packageName))
            SettingsIntent.OnClickSupport -> sendEvent(SettingsEvent.SendEmail(email = "novaknazar@gmail.com"))
        }
    }

    private fun getCurrentLocale(context: Context): Locale {
        val locales = AppCompatDelegate.getApplicationLocales()
        return locales.get(0) ?: context.resources.configuration.locales.get(0)
    }
}

