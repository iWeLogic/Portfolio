package com.iwelogic.portfolio.presentation.main.apps

import com.iwelogic.portfolio.presentation.source.DataSource
import com.iwelogic.portfolio.domain.main.apps.AppsRepository
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
        return AppsRepositoryImp(dataSource)
    }
}