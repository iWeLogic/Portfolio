package com.iwelogic.core_ui.base

sealed class BaseUiEffect {

    class ShowErrorMessage(val message: String) : BaseUiEffect()

    class ShowMessage(val message: String) : BaseUiEffect()
}
