package com.iwelogic.portfolio.presentation.main.news

import com.iwelogic.portfolio.presentation.source.DataSource
import com.iwelogic.portfolio.domain.main.news.NewsRepository
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
    fun provideNewsRepository(dataSource: DataSource): NewsRepository {
        return NewsRepositoryImp(dataSource)
    }
}