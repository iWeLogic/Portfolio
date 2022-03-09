package com.iwelogic.portfolio.domain.register


import com.iwelogic.portfolio.domain.models.RegisterData
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

class RegisterUseCaseImp(private val registerRepository: RegisterRepository) : RegisterUseCase {

    override fun register(data: RegisterData): Flow<Result<Any>> {
        return registerRepository.register(data)
    }
}