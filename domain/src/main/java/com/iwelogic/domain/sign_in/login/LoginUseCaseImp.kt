package com.iwelogic.domain.sign_in.login

import com.iwelogic.core.utils.isEmail
import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.models.SignInDomain
import com.iwelogic.domain.models.UserDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

class LoginUseCaseImp(var loginRepository: LoginRepository, var localUserRepository: LocalUserRepository) : LoginUseCase {

    override fun login(email: String?, password: String?): Flow<Result<UserDomain>> {

        val errors: MutableList<Result.Error> = ArrayList()
        if (!email.isEmail()) errors.add(Result.Error(Result.Error.Code.WRONG_EMAIL))
        if (password.isNullOrEmpty() || password.length < 8) errors.add(Result.Error(Result.Error.Code.WRONG_PASSWORD))
        if (errors.isNotEmpty()) return errors.asFlow()

        val data = SignInDomain(email, password)
        return loginRepository.login(data).onEach { result ->
            if (result is Result.Success<UserDomain>) {
                result.data?.let { user ->
                    localUserRepository.updateUserPreference(user)
                }
            }
            if (result is Result.Error && result.code == Result.Error.Code.NOT_CONFIRMED) {
                loginRepository.resendConfirmation(data.login)
            }
        }
    }
}