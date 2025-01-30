package com.iwelogic.profile.data.mapper

import com.iwelogic.profile.data.dto.*
import com.iwelogic.profile.domain.models.*

fun ContactDto.toContact(): ContactDomain {
    return ContactDomain(
        name = name,
        link = link,
        id = id,
        type = type,
        value = value
    )
}