package com.iwelogic.domain

import com.iwelogic.domain.models.ExistStatus
import kotlinx.coroutines.flow.Flow

interface UserExistUseCase {

    suspend fun checkIsUserExist(): Flow<ExistStatus>
}

