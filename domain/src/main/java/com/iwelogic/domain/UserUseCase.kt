package com.iwelogic.domain

import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    suspend fun getUser(): Flow<UserDomain>
}

