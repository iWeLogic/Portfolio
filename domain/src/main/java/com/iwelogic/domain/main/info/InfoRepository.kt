package com.iwelogic.domain.main.info

import com.iwelogic.domain.models.InfoDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface InfoRepository {

    fun getInfo(): Flow<Result<InfoDomain>>
}