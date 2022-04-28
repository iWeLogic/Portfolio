package com.iwelogic.domain.main.feedbacks

import com.iwelogic.domain.models.FeedbackDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface FeedbacksUseCase {

    fun getFeedbacks(pageSize: Int, offset: Int): Flow<Result<List<FeedbackDomain>>>

    fun addFeedback(feedback: FeedbackDomain): Flow<Result<FeedbackDomain>>
}