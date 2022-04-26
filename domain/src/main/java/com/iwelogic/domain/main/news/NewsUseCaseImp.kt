package com.iwelogic.domain.main.news

import com.iwelogic.domain.models.NewsDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

class NewsUseCaseImp(var newsRepository: NewsRepository) : NewsUseCase {

    override fun getNews(pageSize: Int, offset: Int): Flow<Result<List<NewsDomain>>> {
        print("DOMAIN news:")
        return newsRepository.getNews(pageSize, offset)
    }
}