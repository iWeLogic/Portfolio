package com.iwelogic.presentation.ui.main.news

import com.iwelogic.domain.main.news.NewsRepository
import com.iwelogic.domain.main.news.NewsUseCase
import com.iwelogic.domain.main.news.NewsUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object NewsUseCaseModule {

    @Provides
    fun provide(newsRepository: NewsRepository): NewsUseCase {
        return NewsUseCaseImp(newsRepository)
    }
}