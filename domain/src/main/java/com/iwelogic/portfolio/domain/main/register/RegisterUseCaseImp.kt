package com.iwelogic.portfolio.domain.main.register


import com.iwelogic.portfolio.data.models.RegisterData
import com.iwelogic.portfolio.data.models.Result
import com.iwelogic.portfolio.data.register.RegisterRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCaseImp(private val registerRepository: RegisterRepository) : RegisterUseCase {

    override fun register(data: RegisterData): Flow<Result<Any>> {
        return registerRepository.register(data)
    }
}