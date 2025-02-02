package com.iwelogic.core_ui

import android.annotation.*
import android.content.*
import android.util.*
import dagger.hilt.android.qualifiers.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@Singleton
class ThemeHolder @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object {
        private const val THEME_STORAGE_KEY = "THEME_STORAGE_KEY"
        private const val IS_DARK_KEY = "THEME_KEY"
    }

    private val preferences : SharedPreferences = context.getSharedPreferences(THEME_STORAGE_KEY, Context.MODE_PRIVATE)

    private val _isDarkFlow = MutableStateFlow(isDark)
    val isDarkFlow: StateFlow<Boolean> get() = _isDarkFlow


    var isDark: Boolean
        @SuppressLint("ApplySharedPref")
        set(value) {
            _isDarkFlow.value = value
            preferences.edit().putBoolean(IS_DARK_KEY, value).commit()
        }
        get(){
            Log.w("myLog", "isDark:  ${preferences.getBoolean(IS_DARK_KEY, false)}")
            return preferences.getBoolean(IS_DARK_KEY, false)
        }

    fun setSystemDefault(isDarkTheme : Boolean) {
        if(!preferences.contains(IS_DARK_KEY)){
            isDark = isDarkTheme
        }
    }
}