package com.iwelogic.profile.presentation.mapper

import com.iwelogic.profile.domain.models.*
import com.iwelogic.profile.presentation.models.*

fun JobDomain.toJob(): Job {
    return Job(
        name = name,
        link = link,
        id = id,
        duration = duration,
        position = position
    )
}