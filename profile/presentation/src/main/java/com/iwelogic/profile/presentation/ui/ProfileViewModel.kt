package com.iwelogic.profile.presentation.ui

import androidx.lifecycle.*
import com.iwelogic.core_ui.base.*
import com.iwelogic.profile.domain.use_case.*
import com.iwelogic.profile.presentation.mapper.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.*

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCase: ProfileUseCase) :
    BaseViewModel<ProfileState, ProfileIntent, ProfileUIEffect>(ProfileState.Loading) {

    init {
        onReload()
    }

    private fun onReload() {
        setState(ProfileState.Loading)
        viewModelScope.launch {
            useCase.getHomeData()
                .onSuccess { result ->
                    setState(
                        ProfileState.Main(
                            profile = result.profile.toProfile(),
                            contacts = result.contacts.map { it.toContact() }.sortedBy { it.id },
                            jobs = result.jobs.map { it.toJob() }.sortedBy { it.id }.reversed(),
                            studies = result.studies.map { it.toStudy() }.sortedBy { it.id }.reversed(),
                        )
                    )
                }
                .onFailure {
                    setState(ProfileState.Error)
                }
        }
    }

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.OnClickContact -> {
                if (intent.contact.type == "phone") {
                    sendEvent(ProfileUIEffect.DialPhone(intent.contact.value))
                } else if(intent.contact.link != null){
                    sendEvent(ProfileUIEffect.OpenLink(intent.contact.link))
                }
            }
            is ProfileIntent.OnClickJob -> sendEvent(ProfileUIEffect.OpenLink(intent.job.link))
            ProfileIntent.OnClickReload -> onReload()
        }
    }
}