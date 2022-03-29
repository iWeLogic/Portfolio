package com.iwelogic.portfolio.domain

import com.iwelogic.portfolio.domain.models.DomainUser
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    val userFlow: Flow<DomainUser>

    suspend fun updateUserPreference(user: DomainUser)

    suspend fun clearData()
}