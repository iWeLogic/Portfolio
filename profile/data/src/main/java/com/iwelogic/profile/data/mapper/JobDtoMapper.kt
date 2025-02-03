package com.iwelogic.profile.data.mapper

import com.iwelogic.profile.data.dto.*
import com.iwelogic.profile.domain.models.*

fun JobDto.toJobDomain(): JobDomain {
    return JobDomain(
        name = name,
        link = link,
        id = id,
        duration = duration,
        position = position
    )
}