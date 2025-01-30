package com.iwelogic.profile.presentation.mapper

import com.iwelogic.profile.domain.models.*
import com.iwelogic.profile.presentation.models.*

fun ContactDomain.toContact(): Contact {
    return Contact(
        name = name,
        link = link,
        id = id,
        type = type,
        value = value
    )
}