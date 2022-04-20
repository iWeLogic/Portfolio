package com.iwelogic.data.main.apps

import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.apps.AppsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppsRepositoryModule {

    @Provides
    @Singleton
    fun provideAppsRepository(dataSource: DataSource): AppsRepository {
        return AppsRepositoryImp(dataSource, AppDataDomainMapper())
    }
}