package com.iwelogic.domain.main.news

import com.iwelogic.domain.models.NewsDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

class NewsUseCaseImp(private val newsRepository: NewsRepository) : NewsUseCase {

    override fun getNews(pageSize: Int, offset: Int): Flow<Result<List<NewsDomain>>> {
        return newsRepository.getNews(pageSize, offset)
    }
}