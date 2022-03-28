package com.iwelogic.portfolio.domain.sign_in.register


import android.content.Context
import com.iwelogic.portfolio.domain.R
import com.iwelogic.portfolio.domain.models.RegisterData
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.utils.isEmail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RegisterUseCaseImp(val context: Context, private val registerRepository: RegisterRepository) : RegisterUseCase {

    override fun register(data: RegisterData): Flow<Result<Any>> {
        if (!data.email.isEmail()) {
            return flowOf(Result.Error(Result.Error.Code.WRONG_EMAIL, context.getString(R.string.wrong_email)))
        } else if (data.password.isNullOrEmpty() || data.password.length < 8) {
            return flowOf(Result.Error(Result.Error.Code.PASSWORD_TOO_SHORT, context.getString(R.string.wrong_password)))
        } else if (data.password != data.passwordTwo) {
            return flowOf(Result.Error(Result.Error.Code.PASSWORDS_DONT_MATCH, context.getString(R.string.passwords_dont_match)))
        }
        return registerRepository.register(data)
    }
}