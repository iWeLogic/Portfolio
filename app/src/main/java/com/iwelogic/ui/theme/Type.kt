package com.iwelogic.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.iwelogic.*

// Set of Material typography styles to start with
val openSans = FontFamily(
    Font(R.font.open_sans_bold, FontWeight.Normal),
    Font(R.font.open_sans_bold,FontWeight.Light),
    Font(R.font.open_sans_semi_bold, FontWeight.SemiBold),
    Font(R.font.open_sans_bold, FontWeight.Bold),
    Font(R.font.open_sans_bold, FontWeight.ExtraBold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)