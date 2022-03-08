package com.iwelogic.portfolio.domain.main.login


import com.iwelogic.portfolio.domain.main.main.LocalUserRepository
import com.iwelogic.portfolio.domain.main.models.SignInData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.iwelogic.portfolio.domain.main.models.Result
import com.iwelogic.portfolio.domain.main.models.User

class LoginUseCase @Inject constructor(var loginRepository: LoginRepository, var localUserRepository: LocalUserRepository) {

    fun login(data: SignInData): Flow<Result<User>> {
        return loginRepository.login(data).onEach {
            if (it is Result.Success<User>) {
                it.data?.let {
                    localUserRepository.updateUserPreference(it)
                }
            }
        }
    }
}