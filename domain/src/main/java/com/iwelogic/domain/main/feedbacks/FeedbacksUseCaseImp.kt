package com.iwelogic.domain.main.feedbacks

import com.iwelogic.domain.models.FeedbackDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

class FeedbacksUseCaseImp(private val feedbacksRepository: FeedbacksRepository) : FeedbacksUseCase {

    override fun getFeedbacks(pageSize: Int, offset: Int): Flow<Result<List<FeedbackDomain>>> {
        return feedbacksRepository.getFeedbacks(pageSize, offset)
    }

    override fun addFeedback(feedback: FeedbackDomain): Flow<Result<FeedbackDomain>> {
        return feedbacksRepository.addFeedbacks(feedback)
    }
}