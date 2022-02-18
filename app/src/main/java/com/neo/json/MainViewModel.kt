package com.neo.json

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _jsonState = MutableStateFlow("")
    val jsonState: StateFlow<String> = _jsonState

    fun onValueChange(jsonString: String) {
        _jsonState.value = jsonString
    }
}
