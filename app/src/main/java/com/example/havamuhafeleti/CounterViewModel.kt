package com.example.havamuhafeleti

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CounterViewModel : ViewModel() {

    private val state = MutableStateFlow(0)

    val count = state.asStateFlow()

    fun increase() {
        state.update { it + 1 }
    }
}