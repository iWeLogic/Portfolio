package com.iwelogic.profile.presentation.mapper

import com.iwelogic.profile.domain.models.*
import com.iwelogic.profile.presentation.models.*

fun ProfileDomain.toProfile(): Profile {
    return Profile(
        rate = rate,
        surname = surname,
        name = name,
        about = about,
        avatar = avatar,
        years = years,
        position = position
    )
}
