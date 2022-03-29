package com.iwelogic.portfolio.data.sign_in.register

import com.iwelogic.portfolio.data.models.DataRegister
import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.models.DomainRegister
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.sign_in.register.RegisterRepository
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