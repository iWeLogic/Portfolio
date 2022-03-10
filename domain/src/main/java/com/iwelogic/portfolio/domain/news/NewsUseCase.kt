package com.iwelogic.portfolio.domain.news

import com.iwelogic.portfolio.domain.models.News
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getNews(pageSize: Int, offset: Int): Flow<Result<List<News>>>
}