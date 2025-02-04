package com.iwelogic.profile.presentation.ui

import com.iwelogic.profile.presentation.models.*

sealed class ProfileEvent {
    data class OnClickContact(val contact: Contact) : ProfileEvent()
    data class OnClickJob(val job: Job) : ProfileEvent()
    data object OnClickReload : ProfileEvent()
}