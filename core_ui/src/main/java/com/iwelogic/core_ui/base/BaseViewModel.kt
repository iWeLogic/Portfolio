package com.iwelogic.core_ui.base

import androidx.compose.runtime.*
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, UiEffect, UserEvent>(initialState: State) : ViewModel() {

    private val _state = mutableStateOf(initialState)
    val state: androidx.compose.runtime.State<State> = _state

    private val _uiEffect = Channel<UiEffect>(Channel.BUFFERED)
    private val _baseUiEffect = Channel<BaseUiEffect>(Channel.BUFFERED)

    val uiEffect: Flow<UiEffect> = _uiEffect.receiveAsFlow()
    val baseUiEffect: Flow<BaseUiEffect> = _baseUiEffect.receiveAsFlow()

    protected fun sendUiEffect(uiEffect: UiEffect) {
        viewModelScope.launch {
            _uiEffect.send(uiEffect)
        }
    }

    protected fun sendBaseUiEffect(uiEffect: BaseUiEffect) {
        viewModelScope.launch {
            _baseUiEffect.send(uiEffect)
        }
    }

    protected fun setState(state: State) {
        _state.value = state
    }

    abstract fun obtainEvent(userEvent: UserEvent)
}