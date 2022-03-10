package com.iwelogic.portfolio.data.apps

import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.apps.AppsRepository
import com.iwelogic.portfolio.domain.news.NewsRepository
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