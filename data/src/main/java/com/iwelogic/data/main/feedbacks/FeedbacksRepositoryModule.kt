package com.iwelogic.data.main.feedbacks

import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.feedbacks.FeedbacksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FeedbacksRepositoryModule {

    @Provides
    @Singleton
    fun provide(dataSource: DataSource): FeedbacksRepository {
        return FeedbacksRepositoryImp(dataSource, FeedbacksDataDomainMapper())
    }
}