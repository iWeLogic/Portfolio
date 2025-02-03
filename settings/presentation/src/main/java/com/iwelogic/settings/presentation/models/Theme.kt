package com.iwelogic.settings.presentation.models

import androidx.annotation.*
import com.iwelogic.settings.presentation.*

enum class Theme (val index : Int, @StringRes val title: Int, val isDark: Boolean) {

    LIGHT(0, R.string.light, false),
    DARK(1, R.string.dark, true)
}