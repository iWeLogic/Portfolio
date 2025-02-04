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
    BaseViewModel<ProfileState, ProfileEvent, ProfileUIEffect>(ProfileState.Loading) {

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

    override fun obtainEvent(userEvent: ProfileEvent) {
        when (userEvent) {
            is ProfileEvent.OnClickContact -> {
                if (userEvent.contact.type == "phone") {
                    sendUiEffect(ProfileUIEffect.DialPhone(userEvent.contact.value))
                } else if(userEvent.contact.link != null){
                    sendUiEffect(ProfileUIEffect.OpenLink(userEvent.contact.link))
                }
            }
            is ProfileEvent.OnClickJob -> sendUiEffect(ProfileUIEffect.OpenLink(userEvent.job.link))
            ProfileEvent.OnClickReload -> onReload()
        }
    }
}