package com.iwelogic.portfolio.data.main.news

import com.iwelogic.portfolio.core.Mapper
import com.iwelogic.portfolio.data.models.DataNews
import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.main.news.NewsRepository
import com.iwelogic.portfolio.domain.models.DomainNews
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class NewsRepositoryImp(private val dataSource: DataSource, private val mapper: Mapper<DataNews, DomainNews>) : NewsRepository {
    override fun getNews(pageSize: Int, offset: Int): Flow<Result<List<DomainNews>>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.getNews(pageSize, offset))
            emit(Result.Finish)
        }.map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data?.map { news -> mapper.map(news) })
                is Result.Error -> Result.Error(result.code, result.message)
                is Result.Loading -> Result.Loading
                is Result.Finish -> Result.Loading
            }
        }.flowOn(Dispatchers.IO)
    }
}