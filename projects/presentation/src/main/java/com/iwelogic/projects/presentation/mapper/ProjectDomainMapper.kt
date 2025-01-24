package com.iwelogic.projects.presentation.mapper

import com.iwelogic.projects.domain.models.*
import com.iwelogic.projects.presentation.models.*


fun ProjectDomain.toProject(): Project {
    return Project(
        id = id,
        icon = icon,
        images = images,
        title = title,
        link = link,
        description = description,
        tags = tags
    )
}
