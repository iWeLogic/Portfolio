package com.iwelogic.portfolio.domain.main.apps

import com.iwelogic.portfolio.domain.models.App
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

class AppsUseCaseImp(var appsRepository: AppsRepository) : AppsUseCase {

    override fun getApps(): Flow<Result<List<App>>> {
        return appsRepository.getApps()
    }
}