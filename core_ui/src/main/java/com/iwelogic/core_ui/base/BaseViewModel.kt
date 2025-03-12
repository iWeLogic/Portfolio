package com.iwelogic.core_ui.base

import androidx.compose.runtime.*
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, Intent, Event>(initialState: State) : ViewModel() {

    private val _state = mutableStateOf(initialState)
    val state: androidx.compose.runtime.State<State> = _state

    private val _event = MutableSharedFlow<Event>(replay = 0)
    private val _baseEvent = MutableSharedFlow<BaseEvent>(replay = 0)

    val event: SharedFlow<Event> = _event.asSharedFlow()
    val baseEvent: SharedFlow<BaseEvent> = _baseEvent.asSharedFlow()

    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun sendBaseEvent(event: BaseEvent) {
        viewModelScope.launch {
            _baseEvent.emit(event)
        }
    }

    protected fun setState(state: State) {
        _state.value = state
    }

    abstract fun handleIntent(intent: Intent)
}