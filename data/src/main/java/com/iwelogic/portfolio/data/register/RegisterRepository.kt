package com.iwelogic.portfolio.data.register

import com.iwelogic.portfolio.data.models.RegisterData
import com.iwelogic.portfolio.data.models.Result
import com.iwelogic.portfolio.data.source.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface RegisterRepository {

    fun register(data: RegisterData): Flow<Result<Any>>
}

@InstallIn(SingletonComponent::class)
@Module
object RegisterRepositoryModule {

    @Provides
    @Singleton
    fun provideRegisterRepository(dataSource: DataSource): RegisterRepository {
        return RegisterRepositoryImp(dataSource)
    }
}