package com.iwelogic.data.main.feedbacks

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.FeedbackData
import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.feedbacks.FeedbacksRepository
import com.iwelogic.domain.models.FeedbackDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class FeedbacksRepositoryImp(private val dataSource: DataSource, private val mapper: Mapper<FeedbackData, FeedbackDomain>) : FeedbacksRepository {

    override fun getFeedbacks(pageSize: Int, offset: Int): Flow<Result<List<FeedbackDomain>>> {
        return flow {
            emit(Result.Loading)
            delay(200)
            emit(dataSource.getFeedbacks(pageSize, offset))
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.map { feedback -> mapper.map(feedback) })
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Finish
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun addFeedbacks(data: FeedbackDomain): Flow<Result<FeedbackDomain>> {
        return flow {
            emit(Result.Loading)
            delay(200)
            emit(dataSource.addFeedback(mapper.reverseMap(data)))
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.let { mapper.map(it) })
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Finish
            }
        }.flowOn(Dispatchers.IO)
    }
}