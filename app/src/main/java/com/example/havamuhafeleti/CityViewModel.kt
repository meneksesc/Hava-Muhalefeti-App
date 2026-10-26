package com.example.havamuhafeleti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CityUiState {
    data object Idle : CityUiState
    data object Loading : CityUiState
    data class Success(val cities: List<City>) : CityUiState
    data object Empty : CityUiState
    data class Error(val message: String) : CityUiState
}

class CityViewModel(
    private val repository: CityRepository = CityRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<CityUiState>(CityUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun searchCities(query: String) {
        if (query.isBlank()) {
            _uiState.value = CityUiState.Idle
            return
        }

        viewModelScope.launch {
            _uiState.value = CityUiState.Loading

            try {
                val cities = repository.searchCities(query)

                _uiState.value = if (cities.isEmpty()) {
                    CityUiState.Empty
                } else {
                    CityUiState.Success(cities)
                }
            } catch (e: Exception) {
                _uiState.value = CityUiState.Error(
                    e.message ?: "Bir hata oluştu."
                )
            }
        }
    }
}