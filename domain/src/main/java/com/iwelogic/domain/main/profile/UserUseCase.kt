package com.iwelogic.domain.main.profile

import com.iwelogic.domain.models.Result
import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    fun update(user: UserDomain): Flow<Result<UserDomain>>

    fun getUser(objectId: String?): Flow<Result<UserDomain>>
}