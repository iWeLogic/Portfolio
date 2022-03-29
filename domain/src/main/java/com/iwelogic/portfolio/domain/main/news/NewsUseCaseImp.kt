package com.iwelogic.portfolio.domain.main.news

import com.iwelogic.portfolio.domain.models.News
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

class NewsUseCaseImp(var newsRepository: NewsRepository) : NewsUseCase {

    override fun getNews(pageSize: Int, offset: Int): Flow<Result<List<News>>> {
        return newsRepository.getNews(pageSize, offset)
    }
}