package com.iwelogic.projects.data.mapper

import com.iwelogic.projects.data.dto.*
import com.iwelogic.projects.domain.models.*

fun ProjectDto.toProject(): ProjectDomain {
    return ProjectDomain(
        id = id,
        icon = icon,
        images = images,
        title = title,
        link = link,
        description = description,
        tags = tags
    )
}