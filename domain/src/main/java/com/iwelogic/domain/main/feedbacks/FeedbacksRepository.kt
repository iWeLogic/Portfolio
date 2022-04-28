package com.iwelogic.domain.main.feedbacks

import com.iwelogic.domain.models.FeedbackDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface FeedbacksRepository {
    fun getFeedbacks(pageSize: Int, offset: Int): Flow<Result<List<FeedbackDomain>>>

    fun addFeedbacks(data: FeedbackDomain): Flow<Result<FeedbackDomain>>
}