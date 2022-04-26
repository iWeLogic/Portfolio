package com.iwelogic.domain.main.apps

import com.iwelogic.domain.models.AppDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

class AppsUseCaseImp(var appsRepository: AppsRepository) : AppsUseCase {

    override fun getApps(): Flow<Result<List<AppDomain>>> {
        return appsRepository.getApps()
    }
}