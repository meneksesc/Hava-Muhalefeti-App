package com.example.havamuhafeleti

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = FavoritesRepository(application)

    val favoriteCities = repository.favoriteCities.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val favoriteCityIds = repository.favoriteCityIds.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptySet()
    )

    fun toggleFavorite(city: City) {
        viewModelScope.launch {
            repository.toggleFavorite(city)
        }
    }
}