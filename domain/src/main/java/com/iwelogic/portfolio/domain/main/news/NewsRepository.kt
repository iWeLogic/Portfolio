package com.iwelogic.portfolio.domain.main.news

import com.iwelogic.portfolio.domain.models.News
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(pageSize: Int, offset: Int): Flow<Result<List<News>>>
}