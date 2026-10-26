package com.example.havamuhafeleti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ForecastUiState {
    data object Idle : ForecastUiState
    data object Loading : ForecastUiState
    data class Success(val forecast: ForecastResponse) : ForecastUiState
    data class Error(val message: String) : ForecastUiState
}

class ForecastViewModel(
    private val repository: ForecastRepository = ForecastRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ForecastUiState>(ForecastUiState.Idle)

    val uiState = _uiState.asStateFlow()

    fun loadForecast(latitude: Double, longitude: Double) {

        viewModelScope.launch {
            _uiState.value = ForecastUiState.Loading

            try {
                val forecast = repository.getForecast(
                    latitude = latitude,
                    longitude = longitude
                )

                _uiState.value = ForecastUiState.Success(forecast)

            } catch (e: Exception) {
                _uiState.value = ForecastUiState.Error(
                    e.message ?: "Hava durumu alınamadı."
                )
            }
        }
    }
}