package com.iwelogic.domain

import com.iwelogic.domain.models.ExistStatus

interface UserExistUseCase {

    suspend fun checkIsUserExist(): ExistStatus
}

