package com.iwelogic.profile.presentation.mapper

import com.iwelogic.profile.domain.models.*
import com.iwelogic.profile.presentation.models.*

fun StudyDomain.toStudy(): Study {
    return Study(
        name = name,
        duration = duration,
        id = id
    )
}