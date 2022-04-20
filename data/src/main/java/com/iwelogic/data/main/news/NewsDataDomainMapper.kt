package com.iwelogic.data.main.news

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.DataNews
import com.iwelogic.domain.models.DomainNews

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