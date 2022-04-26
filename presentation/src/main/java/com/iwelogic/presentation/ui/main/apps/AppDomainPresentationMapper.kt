package com.iwelogic.presentation.ui.main.apps

import com.iwelogic.core.Mapper
import com.iwelogic.domain.models.AppDomain
import com.iwelogic.presentation.models.AppPresentation
import javax.inject.Inject

class AppDomainPresentationMapper @Inject constructor() : Mapper<AppDomain, AppPresentation> {

    override fun map(input: AppDomain): AppPresentation {
        return AppPresentation(
            id = input.id,
            title = input.title,
            description = input.description,
            icon = input.icon,
            images = input.images,
            url = input.url,
            releaseDate = input.releaseDate,
            spendHours = input.spendHours,
            tags = input.tags
        )
    }

    override fun reverseMap(input: AppPresentation): AppDomain {
        return AppDomain(
            id = input.id,
            title = input.title,
            description = input.description,
            icon = input.icon,
            images = input.images,
            url = input.url,
            releaseDate = input.releaseDate,
            spendHours = input.spendHours,
            tags = input.tags
        )
    }
}