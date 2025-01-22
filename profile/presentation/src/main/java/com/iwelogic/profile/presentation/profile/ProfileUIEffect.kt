package com.iwelogic.profile.presentation.profile

sealed class ProfileUIEffect {
    data object OpenContacts : ProfileUIEffect()
    data object OpenProjects : ProfileUIEffect()
    data object OpenAbout : ProfileUIEffect()
}