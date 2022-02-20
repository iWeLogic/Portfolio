package com.iwelogic.domain.main.login


import com.iwelogic.domain.main.local_user.LocalUserRepository
import com.iwelogic.domain.main.models.SignInData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.iwelogic.domain.main.models.Result
import com.iwelogic.domain.main.models.User

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