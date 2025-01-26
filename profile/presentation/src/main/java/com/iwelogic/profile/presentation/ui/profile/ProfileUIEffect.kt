package com.iwelogic.profile.presentation.ui.profile

sealed class ProfileUIEffect {
    data object OpenContacts : ProfileUIEffect()
    data object OpenProjects : ProfileUIEffect()
    data object OpenAbout : ProfileUIEffect()
}