package com.iwelogic.domain.main.profile

import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class UserUseCaseImp(private val userRepository: UserRepository,private val localUserRepository: LocalUserRepository) : UserUseCase {

    override fun update(user: UserDomain): Flow<Result<UserDomain>> {
        return userRepository.update(user).onEach {
            if (it is Result.Success) {
                it.data?.let { it1 -> localUserRepository.updateUserPreference(it1) }
            }
        }
    }

    override fun getUser(objectId: String?): Flow<Result<UserDomain>> {
        return userRepository.get(objectId).onEach {
            if (it is Result.Success) {
                it.data?.let { it1 -> localUserRepository.updateUserPreference(it1) }
            }
        }
    }
}