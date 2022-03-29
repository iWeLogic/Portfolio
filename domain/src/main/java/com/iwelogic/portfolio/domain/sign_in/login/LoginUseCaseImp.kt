package com.iwelogic.portfolio.domain.sign_in.login

import com.iwelogic.portfolio.domain.LocalUserRepository
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.models.SignInData
import com.iwelogic.portfolio.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class LoginUseCaseImp(var loginRepository: LoginRepository, var localUserRepository: LocalUserRepository) : LoginUseCase {

    override fun login(data: SignInData): Flow<Result<User>> {
        return loginRepository.login(data).onEach { result ->
            if (result is Result.Success<User>) {
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