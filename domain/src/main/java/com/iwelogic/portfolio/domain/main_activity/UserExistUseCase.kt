package com.iwelogic.portfolio.domain.main_activity

import com.iwelogic.portfolio.domain.models.ExistStatus

interface UserExistUseCase {

    suspend fun checkIsUserExist(): ExistStatus
}

