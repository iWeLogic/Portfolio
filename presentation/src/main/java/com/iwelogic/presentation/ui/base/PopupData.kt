package com.iwelogic.presentation.ui.base

data class PopupData(
    val text: String? = null,
    val btnCancelTitle: String? = null,
    val btnOkTitle: String? = "OK",
    val btnCancelCallBack: (() -> Unit)? = null,
    val btnOkCallBack: (() -> Unit)? = null
)