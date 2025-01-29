package com.iwelogic.projects.data.mapper

import com.iwelogic.projects.data.dto.*
import com.iwelogic.projects.domain.models.*

fun ProjectDto.toProject(): ProjectDomain {
    return ProjectDomain(
        id = id,
        icon = icon,
        images = images,
        name = name,
        link = link,
        description = description,
        tags = tags,
        rating = rating,
        downloads = downloads
    )
}