package com.iwelogic.portfolio.domain.login


import com.iwelogic.portfolio.data.login.LoginRepository
import com.iwelogic.portfolio.data.local_user.LocalUserRepository
import com.iwelogic.portfolio.data.models.Result
import com.iwelogic.portfolio.data.models.SignInData
import com.iwelogic.portfolio.data.models.User
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