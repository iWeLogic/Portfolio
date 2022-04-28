package com.iwelogic.domain

import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    val userFlow: Flow<UserDomain>

    suspend fun updateUserPreference(user: UserDomain)

    suspend fun clearData()
}