package com.iwelogic.profile.presentation.ui

import com.iwelogic.profile.presentation.models.*

sealed class ProfileState {
    data object Loading : ProfileState()
    data object Error : ProfileState()
    data class Main(
        val profile: Profile,
        val contacts: List<Contact>,
        val isContactsExpanded: Boolean = true,
        val jobs: List<Job>,
        val studies: List<Study>
    ) : ProfileState()
}