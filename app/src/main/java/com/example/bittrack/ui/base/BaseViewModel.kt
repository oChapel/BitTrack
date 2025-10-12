package com.example.bittrack.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<STATE, EVENT>(initialState: STATE) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected fun updateState(block: (STATE) -> STATE) {
        _uiState.update { state -> block(state) }
    }

    abstract fun onEvent(event: EVENT)
}
