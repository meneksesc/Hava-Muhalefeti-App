package com.example.havamuhafeleti

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val current: CurrentResponse?,
    val hourly: HourlyResponse?,
    val daily: DailyResponse?
)

data class CurrentResponse(
    val time: String,

    @SerializedName("temperature_2m")
    val temperature: Double,

    @SerializedName("weather_code")
    val weatherCode: Int,

    @SerializedName("apparent_temperature")
    val apparentTemperature: Double?,

    @SerializedName("relative_humidity_2m")
    val relativeHumidity: Int?,

    @SerializedName("wind_speed_10m")
    val windSpeed: Double?
)

data class HourlyResponse(
    val time: List<String>?,

    @SerializedName("temperature_2m")
    val temperature: List<Double?>?,

    @SerializedName("weather_code")
    val weatherCode: List<Int?>?,

    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int?>?
)

data class DailyResponse(
    val time: List<String>?,

    @SerializedName("temperature_2m_max")
    val temperatureMax: List<Double?>?,

    @SerializedName("temperature_2m_min")
    val temperatureMin: List<Double?>?,

    @SerializedName("weather_code")
    val weatherCode: List<Int?>?,

    @SerializedName("precipitation_probability_max")
    val precipitationProbabilityMax: List<Int?>?
)