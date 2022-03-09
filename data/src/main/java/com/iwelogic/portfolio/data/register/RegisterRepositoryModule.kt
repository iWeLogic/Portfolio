package com.iwelogic.portfolio.data.register

import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RegisterRepositoryModule {

    @Provides
    @Singleton
    fun provideRegisterRepository(dataSource: DataSource): RegisterRepository {
        return RegisterRepositoryImp(dataSource)
    }
}