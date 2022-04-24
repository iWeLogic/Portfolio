package com.iwelogic.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PresentationApp(

    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val icon: String? = null,
    val images: List<String>? = null,
    val url: String? = null

) : Parcelable
