package com.iwelogic.settings.presentation.models

import androidx.annotation.*
import com.iwelogic.settings.presentation.*

enum class NotificationStatus (val index : Int, @StringRes val title: Int, val isOn: Boolean) {

    OFF(0, R.string.off, false),
    ON(1, R.string.on, true)
}