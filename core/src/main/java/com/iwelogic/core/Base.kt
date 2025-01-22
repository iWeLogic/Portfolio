package com.iwelogic.core

import androidx.compose.ui.graphics.*

fun String.toColor(): Color {
    return if (this.startsWith("#")) {
        when (this.length) {
            7 -> Color(android.graphics.Color.parseColor(this))
            9 -> Color(android.graphics.Color.parseColor(this))
            else ->  throw IllegalArgumentException("Invalid hex color format")
        }
    } else {
        throw IllegalArgumentException("Hex color must start with '#'")
    }
}