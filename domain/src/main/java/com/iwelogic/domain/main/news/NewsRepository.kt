package com.iwelogic.domain.main.news

import com.iwelogic.domain.models.NewsDomain
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(pageSize: Int, offset: Int): Flow<Result<List<NewsDomain>>>
}