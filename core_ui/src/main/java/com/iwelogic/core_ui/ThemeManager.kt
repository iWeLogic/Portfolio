package com.iwelogic.core_ui

import android.annotation.*
import android.content.*
import dagger.hilt.android.qualifiers.*
import kotlinx.coroutines.flow.*
import javax.inject.*
import androidx.core.content.edit

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
            preferences.edit(commit = true) { putBoolean(IS_DARK_KEY, value) }
        }
        get(){
            return preferences.getBoolean(IS_DARK_KEY, false)
        }

    fun setSystemDefault(isDarkTheme : Boolean) {
        if(!preferences.contains(IS_DARK_KEY)){
            isDark = isDarkTheme
        }
    }
}