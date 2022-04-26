package com.iwelogic.data.main.news

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.NewsData
import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.news.NewsRepository
import com.iwelogic.domain.models.NewsDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class NewsRepositoryImp(private val dataSource: DataSource, private val mapper: Mapper<NewsData, NewsDomain>) : NewsRepository {

    override fun getNews(pageSize: Int, offset: Int): Flow<Result<List<NewsDomain>>> {
        return flow {
            emit(Result.Loading)
            delay(200)
            emit(dataSource.getNews(pageSize, offset))
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.map { news -> mapper.map(news) })
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Finish
            }
        }.flowOn(Dispatchers.IO)
    }
}