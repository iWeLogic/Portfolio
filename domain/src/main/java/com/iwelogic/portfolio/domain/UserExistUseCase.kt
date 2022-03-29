package com.iwelogic.portfolio.domain

import com.iwelogic.portfolio.domain.models.ExistStatus

interface UserExistUseCase {

    suspend fun checkIsUserExist(): ExistStatus
}

