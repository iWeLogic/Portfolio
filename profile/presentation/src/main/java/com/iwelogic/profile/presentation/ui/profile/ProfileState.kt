package com.iwelogic.profile.presentation.ui.profile

import com.iwelogic.profile.presentation.models.*

sealed class ProfileState {
    data object Loading : ProfileState()
    data object Error : ProfileState()
    data class Main(val profile: Profile) : ProfileState()
}