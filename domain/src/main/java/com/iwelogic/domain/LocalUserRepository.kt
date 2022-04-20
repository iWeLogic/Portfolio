package com.iwelogic.domain

import com.iwelogic.domain.models.DomainUser
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    val userFlow: Flow<DomainUser>

    suspend fun updateUserPreference(user: DomainUser)

    suspend fun clearData()
}