package com.iwelogic.presentation.ui.main.feedbacks

import com.iwelogic.domain.main.feedbacks.FeedbacksRepository
import com.iwelogic.domain.main.feedbacks.FeedbacksUseCase
import com.iwelogic.domain.main.feedbacks.FeedbacksUseCaseImp
import com.iwelogic.domain.main.news.NewsRepository
import com.iwelogic.domain.main.news.NewsUseCase
import com.iwelogic.domain.main.news.NewsUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object FeedbacksUseCaseModule {

    @Provides
    fun provide(feedbacksRepository: FeedbacksRepository): FeedbacksUseCase {
        return FeedbacksUseCaseImp(feedbacksRepository)
    }
}