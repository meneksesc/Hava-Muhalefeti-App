package com.example.havamuhafeleti

class CityRepository(
    private val api: CityApi = ApiClient.cityApi
) {
    suspend fun searchCities(query: String): List<City> {
        val response = api.search(name = query)
        return response.results.orEmpty()
    }
}