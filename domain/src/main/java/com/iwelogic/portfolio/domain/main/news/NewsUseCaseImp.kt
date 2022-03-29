package com.iwelogic.portfolio.domain.main.news

import com.iwelogic.portfolio.domain.models.DomainNews
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

class NewsUseCaseImp(var newsRepository: NewsRepository) : NewsUseCase {

    override fun getNews(pageSize: Int, offset: Int): Flow<Result<List<DomainNews>>> {
        print("DOMAIN news:")
        return newsRepository.getNews(pageSize, offset)
    }
}