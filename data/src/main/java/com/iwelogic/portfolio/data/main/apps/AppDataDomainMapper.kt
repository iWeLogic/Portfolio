package com.iwelogic.portfolio.data.main.apps

import com.iwelogic.portfolio.core.Mapper
import com.iwelogic.portfolio.data.models.DataApp
import com.iwelogic.portfolio.domain.models.DomainApp

class AppDataDomainMapper : Mapper<DataApp, DomainApp> {

    override fun map(input: DataApp): DomainApp {
        return DomainApp(
            id = input.id,
            title = input.title,
            description = input.description,
            icon = input.icon
        )
    }
}