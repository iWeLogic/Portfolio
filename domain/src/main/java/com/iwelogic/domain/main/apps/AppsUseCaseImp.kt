package com.iwelogic.domain.main.apps

import com.iwelogic.domain.models.DomainApp
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

class AppsUseCaseImp(var appsRepository: AppsRepository) : AppsUseCase {

    override fun getApps(): Flow<Result<List<DomainApp>>> {
        return appsRepository.getApps()
    }
}