package com.iwelogic.domain.main.profile

import com.iwelogic.domain.models.Result
import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun update(userData: UserDomain): Flow<Result<UserDomain>>
    fun get(objectId: String?): Flow<Result<UserDomain>>
}