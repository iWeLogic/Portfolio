package com.iwelogic.domain.main.info

import com.iwelogic.domain.models.InfoDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

class InfoUseCaseImp(var infoRepository: InfoRepository) : InfoUseCase {

    override fun getInfo(): Flow<Result<InfoDomain>> {
        return infoRepository.getInfo()
    }
}