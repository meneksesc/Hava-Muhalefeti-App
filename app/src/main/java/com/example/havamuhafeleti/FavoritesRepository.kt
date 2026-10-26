package com.example.havamuhafeleti

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.favoritesDataStore by preferencesDataStore(
    name = "favorite_cities"
)

class FavoritesRepository(
    private val context: Context
) {
    private val gson = Gson()

    private val favoritesKey =
        stringPreferencesKey("favorite_cities_json")

    private val cityListType =
        object : TypeToken<List<City>>() {}.type

    val favoriteCities: Flow<List<City>> =
        context.favoritesDataStore.data.map { preferences ->
            val json = preferences[favoritesKey]

            if (json.isNullOrBlank()) {
                emptyList()
            } else {
                try {
                    gson.fromJson<List<City>>(json, cityListType)
                        ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

    val favoriteCityIds: Flow<Set<String>> =
        favoriteCities.map { cities ->
            cities.map { it.id.toString() }.toSet()
        }

    suspend fun toggleFavorite(city: City) {
        context.favoritesDataStore.edit { preferences ->

            val currentJson = preferences[favoritesKey]

            val cities: MutableList<City> =
                if (currentJson.isNullOrBlank()) {
                    mutableListOf()
                } else {
                    try {
                        val saved: List<City> =
                            gson.fromJson(currentJson, cityListType)
                                ?: emptyList()
                        saved.toMutableList()
                    } catch (e: Exception) {
                        mutableListOf()
                    }
                }

            if (cities.any { it.id == city.id }) {
                cities.removeAll { it.id == city.id }
            } else {
                cities.add(city)
            }

            preferences[favoritesKey] = gson.toJson(cities)
        }
    }
}