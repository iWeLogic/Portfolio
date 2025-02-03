package com.iwelogic.profile.domain.models

data class HomeData(
    val profile: ProfileDomain,
    val contacts: List<ContactDomain>,
    val jobs: List<JobDomain>,
    val studies: List<StudyDomain>
)
