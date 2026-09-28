package com.example.curls.settings

import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UiEngineSettings {
    val scope = MainScope()

    val useComposeUi: StateFlow<Boolean>
    field = MutableStateFlow(true)

    fun setUseComposeUi(newValue: Boolean) {
        useComposeUi.update { newValue }
    }

    fun observe(onChange: (Boolean) -> Unit): Job {
        return scope.launch { useComposeUi.collectLatest { onChange(it) } }
    }
}