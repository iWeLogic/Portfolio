package com.iwelogic.profile.data.mapper

import com.iwelogic.profile.data.dto.*
import com.iwelogic.profile.domain.models.*

fun ProfileDto.toProfileDomain(): ProfileDomain {
    return ProfileDomain(
        rate = rate,
        surname = surname,
        name = name,
        about = about,
        avatar = avatar,
        years = years,
        position = position
    )
}