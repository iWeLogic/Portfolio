package com.iwelogic.domain.sign_in.register


import com.iwelogic.domain.models.DomainRegister
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

class RegisterUseCaseImp(private val registerRepository: RegisterRepository) : RegisterUseCase {

    override fun register(data: DomainRegister): Flow<Result<Any>> {
        return registerRepository.register(data)
    }
}