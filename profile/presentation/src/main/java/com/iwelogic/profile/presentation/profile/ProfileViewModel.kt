package com.iwelogic.profile.presentation.profile

import androidx.compose.runtime.*
import androidx.lifecycle.*

class ProfileViewModel : ViewModel() {

    private val _state = mutableStateOf(ProfileState.Main())
    val state: State<ProfileState> = _state
}