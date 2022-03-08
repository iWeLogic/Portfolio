package com.iwelogic.portfolio.domain.main.main

import com.iwelogic.portfolio.domain.main.models.User
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    var userFlow: Flow<User>

    suspend fun updateUserPreference(user: User)
}