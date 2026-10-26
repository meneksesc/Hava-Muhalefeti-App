package com.example.havamuhafeleti

import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String =
            "temperature_2m,weather_code,apparent_temperature,relative_humidity_2m,wind_speed_10m",
        @Query("hourly") hourly: String =
            "temperature_2m,weather_code,precipitation_probability",
        @Query("daily") daily: String =
            "weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max",
        @Query("forecast_days") forecastDays: Int = 7,
        @Query("timezone") timezone: String = "auto"
    ): ForecastResponse
}