package com.iwelogic.data.sign_in.register

import com.iwelogic.data.models.DataRegister
import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.models.DomainRegister
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.sign_in.register.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterRepositoryImp constructor(private val dataSource: DataSource) : RegisterRepository {

    override fun register(data: DomainRegister): Flow<Result<Any>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.register(DataRegister(data.email, data.image, data.firstName, data.lastName, data.password)))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }


}