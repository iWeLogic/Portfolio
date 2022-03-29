package com.iwelogic.portfolio.presentation.main.news

import com.iwelogic.portfolio.domain.main.news.NewsRepository
import com.iwelogic.portfolio.domain.main.news.NewsUseCase
import com.iwelogic.portfolio.domain.main.news.NewsUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object NewsUseCaseModule {

    @Provides
    fun provideNewsUseCase(newsRepository: NewsRepository): NewsUseCase {
        return NewsUseCaseImp(newsRepository)
    }
}