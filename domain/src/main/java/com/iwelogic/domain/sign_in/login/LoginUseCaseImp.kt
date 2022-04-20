package com.iwelogic.domain.sign_in.login

import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.models.DomainSignIn
import com.iwelogic.domain.models.DomainUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class LoginUseCaseImp(var loginRepository: LoginRepository, var localUserRepository: LocalUserRepository) : LoginUseCase {

    override fun login(data: DomainSignIn): Flow<Result<DomainUser>> {
        return loginRepository.login(data).onEach { result ->
            if (result is Result.Success<DomainUser>) {
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