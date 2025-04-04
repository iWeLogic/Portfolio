package com.iwelogic.profile.data.mapper

import com.iwelogic.profile.data.dto.*
import com.iwelogic.profile.domain.models.*

fun ContactDto.toContactDomain(): ContactDomain {
    return ContactDomain(
        name = name,
        link = link,
        id = id,
        type = type,
        value = value
    )
}