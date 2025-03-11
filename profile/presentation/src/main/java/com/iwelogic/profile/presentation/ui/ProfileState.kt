package com.iwelogic.profile.presentation.ui

import com.iwelogic.profile.presentation.models.*

sealed class ProfileState {
    data object Loading : ProfileState()
    data object Error : ProfileState()
    data class Main(
        val profile: Profile,
        val contacts: List<Contact>,
        val jobs: List<Job>,
        val studies: List<Study>
    ) : ProfileState() {

        companion object {
            val preview = Main(
                profile = Profile.preview,
                contacts = listOf(Contact.preview),
                jobs = listOf(Job.preview),
                studies = listOf(Study.preview),
            )
        }
    }
}