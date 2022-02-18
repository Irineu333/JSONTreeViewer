package com.neo.jsonviewer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _jsonState = MutableStateFlow("")
    val jsonState: StateFlow<String> = _jsonState

    fun onValueChange(jsonString: String) {
        _jsonState.value = jsonString
    }
}
