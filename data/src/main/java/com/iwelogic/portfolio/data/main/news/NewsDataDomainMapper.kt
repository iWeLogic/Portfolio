package com.iwelogic.portfolio.data.main.news

import com.iwelogic.portfolio.core.Mapper
import com.iwelogic.portfolio.data.models.DataNews
import com.iwelogic.portfolio.domain.models.DomainNews

class NewsDataDomainMapper : Mapper<DataNews, DomainNews> {

    override fun map(input: DataNews): DomainNews {
        return DomainNews(
            id = input.id,
            title = input.title,
            description = input.description,
            image = input.image,
        )
    }
}