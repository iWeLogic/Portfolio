package com.iwelogic.data.main.news

import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.news.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NewsRepositoryModule {

    @Provides
    @Singleton
    fun provide(dataSource: DataSource): NewsRepository {
        return NewsRepositoryImp(dataSource, NewsDataDomainMapper())
    }
}