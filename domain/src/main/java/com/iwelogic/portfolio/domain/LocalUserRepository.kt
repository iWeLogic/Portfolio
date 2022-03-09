package com.iwelogic.portfolio.domain

import com.iwelogic.portfolio.domain.models.User
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    val userFlow: Flow<User>

    suspend fun updateUserPreference(user: User)

    suspend fun clearData()
}