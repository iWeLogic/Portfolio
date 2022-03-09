package com.iwelogic.portfolio.domain.login

import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.models.SignInData
import com.iwelogic.portfolio.domain.models.User
import com.iwelogic.portfolio.domain.LocalUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class LoginUseCaseImp(var loginRepository: LoginRepository, var localUserRepository: LocalUserRepository) : LoginUseCase {

    override fun login(data: SignInData): Flow<Result<User>> {
        return loginRepository.login(data).onEach {
            if (it is Result.Success<User>) {
                it.data?.let {
                    localUserRepository.updateUserPreference(it)
                }
            }
        }
    }
}