package com.iwelogic.ui.sign_in

import com.iwelogic.ui.base.BaseNavigator

interface SignInNavigator : BaseNavigator {

    fun openRegister()

    fun openForgotPassword(email: String?)
}