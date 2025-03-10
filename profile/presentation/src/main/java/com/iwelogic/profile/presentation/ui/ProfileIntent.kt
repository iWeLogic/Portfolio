package com.iwelogic.profile.presentation.ui

import com.iwelogic.profile.presentation.models.*

sealed class ProfileIntent {
    data class OnClickContact(val contact: Contact) : ProfileIntent()
    data class OnClickJob(val job: Job) : ProfileIntent()
    data object OnClickReload : ProfileIntent()
}