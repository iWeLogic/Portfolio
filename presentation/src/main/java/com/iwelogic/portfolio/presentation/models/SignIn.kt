package com.iwelogic.portfolio.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignIn(
    val login: String? = null,
    val password: String? = null
) : Parcelable
