package com.iwelogic.profile.data.mapper

import com.iwelogic.profile.data.dto.*
import com.iwelogic.profile.domain.models.*

fun StudyDto.toStudyDomain(): StudyDomain {
    return StudyDomain(
        name = name,
        duration = duration,
        id = id
    )
}