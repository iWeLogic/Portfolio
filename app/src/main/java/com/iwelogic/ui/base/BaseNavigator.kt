package com.iwelogic.ui.base

interface BaseNavigator {

    fun showToast(msg: String?)

    fun showToast(msg: Int)

    fun close()

    fun openLogin()

    fun openMain(isFirstLaunch: Boolean = false)

    fun showPopupWarning(message: String)

    fun openOnboarding()
}