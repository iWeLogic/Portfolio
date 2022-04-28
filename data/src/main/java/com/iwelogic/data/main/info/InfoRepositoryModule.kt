package com.iwelogic.data.main.info

import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.info.InfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InfoRepositoryModule {

    @Provides
    @Singleton
    fun provide(dataSource: DataSource): InfoRepository {
        return InfoRepositoryImp(dataSource, InfoDataDomainMapper())
    }
}