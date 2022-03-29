package com.iwelogic.portfolio.presentation.main.news

import com.iwelogic.portfolio.presentation.source.DataSource
import com.iwelogic.portfolio.domain.models.News
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.main.news.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepositoryImp(private val dataSource: DataSource) : NewsRepository {

    override fun getNews(pageSize: Int, offset: Int): Flow<Result<List<News>>> {
        return flow {
            emit(Result.Loading)
            delay(500)
            emit(dataSource.getNews(pageSize, offset))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }
}