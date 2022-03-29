package com.iwelogic.portfolio.domain.main.news

import com.iwelogic.portfolio.domain.models.DomainNews
import com.iwelogic.portfolio.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getNews(pageSize: Int, offset: Int): Flow<Result<List<DomainNews>>>
}