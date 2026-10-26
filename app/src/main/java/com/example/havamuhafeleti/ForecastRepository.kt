package com.example.havamuhafeleti

class ForecastRepository(
    private val api: ForecastApi = ApiClient.forecastApi
) {

    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): ForecastResponse {

        return api.getForecast(
            latitude = latitude,
            longitude = longitude
        )
    }
}