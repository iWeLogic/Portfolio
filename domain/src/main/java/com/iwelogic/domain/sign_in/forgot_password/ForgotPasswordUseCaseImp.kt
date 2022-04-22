package com.iwelogic.domain.sign_in.forgot_password

import com.iwelogic.core.utils.isEmail
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class ForgotPasswordUseCaseImp(var forgotPasswordRepository: ForgotPasswordRepository) : ForgotPasswordUseCase {

    override fun remember(email: String?): Flow<Result<Any>> {

        val errors: MutableList<Result.Error> = ArrayList()
        if (!email.isEmail()) errors.add(Result.Error(Result.Error.Code.WRONG_EMAIL))
        if (errors.isNotEmpty()) return errors.asFlow()

        return forgotPasswordRepository.remember(email!!)
    }
}