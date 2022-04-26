package com.iwelogic.data.main.apps

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.DataApp
import com.iwelogic.domain.models.DomainApp

class AppDataDomainMapper : Mapper<DataApp, DomainApp> {

    override fun map(input: DataApp): DomainApp {
        return DomainApp(
            id = input.id,
            title = input.title,
            description = input.description,
            icon = input.icon,
            images = input.images,
            url = input.url,
            releaseDate = input.releaseDate,
            spendHours = input.spendHours,
            tags = input.tags,
        )
    }
}