package com.iwelogic.presentation.ui.main.apps

import com.iwelogic.core.Mapper
import com.iwelogic.domain.models.DomainApp
import com.iwelogic.presentation.models.PresentationApp
import javax.inject.Inject

class AppDomainPresentationMapper @Inject constructor() : Mapper<DomainApp, PresentationApp> {

    override fun map(input: DomainApp): PresentationApp {
        return PresentationApp(
            id = input.id,
            title = input.title,
            description = input.description,
            icon = input.icon,
            images = input.images,
            url = input.url
        )
    }
}