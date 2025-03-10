package com.iwelogic.core_ui.base

sealed class BaseEvent {

    class ShowErrorMessage(val message: String) : BaseEvent()

    class ShowMessage(val message: String) : BaseEvent()
}
