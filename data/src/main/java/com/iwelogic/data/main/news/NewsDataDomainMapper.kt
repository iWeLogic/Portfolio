package com.iwelogic.data.main.news

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.NewsData
import com.iwelogic.domain.models.NewsDomain

class NewsDataDomainMapper : Mapper<NewsData, NewsDomain> {

    override fun map(input: NewsData): NewsDomain {
        return NewsDomain(
            id = input.id,
            title = input.title,
            description = input.description,
            image = input.image
        )
    }

    override fun reverseMap(input: NewsDomain): NewsData {
        return NewsData(
            id = input.id,
            title = input.title,
            description = input.description,
            image = input.image
        )
    }
}