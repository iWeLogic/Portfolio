package com.iwelogic.profile.domain.models

data class HomeData(
    val profile: ProfileDomain,
    val contacts: List<ContactDomain>
)
