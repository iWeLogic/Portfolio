package com.iwelogic.data.main.apps

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.AppData
import com.iwelogic.domain.models.AppDomain

class AppDataDomainMapper : Mapper<AppData, AppDomain> {

    override fun map(input: AppData): AppDomain {
        return AppDomain(
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