package com.example.havamuhafeleti

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val cityRetrofit = Retrofit.Builder()
        .baseUrl("https://geocoding-api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val cityApi: CityApi =
        cityRetrofit.create(CityApi::class.java)

    private val forecastRetrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val forecastApi: ForecastApi =
        forecastRetrofit.create(ForecastApi::class.java)
}