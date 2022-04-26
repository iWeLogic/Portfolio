package com.iwelogic.presentation.ui.main.news

import com.iwelogic.core.Mapper
import com.iwelogic.domain.models.NewsDomain
import com.iwelogic.presentation.models.NewsPresentation
import javax.inject.Inject

class NewsDomainPresentationMapper @Inject constructor() : Mapper<NewsDomain, NewsPresentation> {

    override fun map(input: NewsDomain): NewsPresentation {
        return NewsPresentation(
            id = input.id,
            title = input.title,
            description = input.description,
            image = input.image
        )
    }
}