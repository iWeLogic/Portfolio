package com.iwelogic.portfolio.ui.forgot_password

import com.iwelogic.portfolio.domain.forgot_password.ForgotPasswordUseCase
import com.iwelogic.portfolio.ui.base.BaseViewModel
import javax.inject.Inject

class ForgotPasswordViewModel @Inject constructor(var forgotPasswordUseCase: ForgotPasswordUseCase) : BaseViewModel() {

}