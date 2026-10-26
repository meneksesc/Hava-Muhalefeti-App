package com.example.havamuhafeleti

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ForecastScreen(
    city: City,
    onBack: () -> Unit,
    forecastViewModel: ForecastViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val uiState by forecastViewModel.uiState.collectAsStateWithLifecycle()

    val favoriteCityIds by favoritesViewModel.favoriteCityIds
        .collectAsStateWithLifecycle()

    val isFavorite = city.id.toString() in favoriteCityIds

    var selectedActivity by rememberSaveable(city.id) {
        mutableStateOf("Piknik")
    }

    LaunchedEffect(city.id) {
        forecastViewModel.loadForecast(
            latitude = city.latitude,
            longitude = city.longitude
        )
    }

    val weatherCode = (uiState as? ForecastUiState.Success)
        ?.forecast
        ?.current
        ?.weatherCode ?: -1

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(getWeatherBackground(weatherCode))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(onClick = onBack) {
                Text("← Şehir aramaya dön", color = Color.White)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = city.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                IconButton(
                    onClick = { favoritesViewModel.toggleFavorite(city) }
                ) {
                    Icon(
                        imageVector = if (isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favori",
                        tint = if (isFavorite)
                            Color(0xFFFF6B81)
                        else
                            Color.White
                    )
                }
            }

            Text(city.subtitle, color = Color.White)

            when (val state = uiState) {
                ForecastUiState.Idle -> Text("Hava durumu hazırlanıyor...")
                ForecastUiState.Loading -> CircularProgressIndicator()

                is ForecastUiState.Error -> {
                    Text("Hata: ${state.message}")
                }

                is ForecastUiState.Success -> {
                    val current = state.forecast.current

                    if (current != null) {
                        val weather = getWeatherAppearance(current.weatherCode)

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(weather.icon, fontSize = 54.sp)
                            Text(
                                "${current.temperature.toInt()}°",
                                fontSize = 76.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                            Text(
                                weather.description,
                                fontSize = 22.sp,
                                color = Color.White
                            )
                        }

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("Hissedilen: ${current.apparentTemperature ?: "—"} °C")
                                Text("Nem: ${current.relativeHumidity ?: "—"}%")
                                Text("Rüzgâr: ${current.windSpeed ?: "—"} km/sa")
                            }
                        }

                        val rainProbability = state.forecast.hourly
                            ?.precipitationProbability
                            .orEmpty()
                            .let { probabilities ->
                                val times = state.forecast.hourly
                                    ?.time.orEmpty()

                                val index = times.indexOfFirst {
                                    it >= current.time
                                }

                                if (index >= 0)
                                    probabilities.getOrNull(index) ?: 0
                                else
                                    0
                            }

                        val indexRating = calculateWeatherIndex(
                            temperature = current.temperature,
                            windSpeed = current.windSpeed ?: 0.0,
                            weatherCode = current.weatherCode,
                            rainProbability = rainProbability
                        )

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    "Hava Muhalefeti Endeksi",
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Text(
                                    "${indexRating.score}/100",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                LinearProgressIndicator(
                                    progress = { indexRating.score / 100f },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(indexRating.message)
                            }
                        }

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    "Planın Hava Muhalefetine Takılır mı?",
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Text("Bir etkinlik seç:")

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(activityOptions) { activity ->
                                        androidx.compose.material3.FilterChip(
                                            selected = selectedActivity == activity.name,
                                            onClick = {
                                                selectedActivity = activity.name
                                            },
                                            label = {
                                                Text("${activity.icon} ${activity.name}")
                                            }
                                        )
                                    }
                                }

                                val rating = calculateActivityRating(
                                    activity = selectedActivity,
                                    temperature = current.temperature,
                                    windSpeed = current.windSpeed ?: 0.0,
                                    weatherCode = current.weatherCode,
                                    rainProbability = rainProbability
                                )

                                Text(
                                    "${rating.score}/100",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                LinearProgressIndicator(
                                    progress = { rating.score / 100f },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(rating.message)
                            }
                        }

                        val advices = getWeatherAdvice(
                            weatherCode = current.weatherCode,
                            temperature = current.temperature,
                            windSpeed = current.windSpeed ?: 0.0
                        )

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    "Bugün Ne Yapmalı?",
                                    style = MaterialTheme.typography.titleLarge
                                )

                                advices.forEach { advice ->
                                    Text("${advice.icon} ${advice.message}")
                                }
                            }
                        }
                    }

                    val hourly = state.forecast.hourly

                    if (hourly != null) {
                        Text(
                            "Saatlik Hava Durumu",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        val times = hourly.time.orEmpty()
                        val temperatures = hourly.temperature.orEmpty()
                        val currentTime = current?.time.orEmpty()

                        val startIndex = times.indexOfFirst {
                            it >= currentTime
                        }.let {
                            if (it == -1) 0 else it
                        }

                        val hourlyItems = (
                                startIndex until minOf(
                                    startIndex + 24,
                                    times.size,
                                    temperatures.size
                                )
                                ).toList()

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(hourlyItems) { index ->
                                val code = hourly.weatherCode
                                    .orEmpty()
                                    .getOrNull(index) ?: -1

                                val weather = getWeatherAppearance(code)

                                Card(modifier = Modifier.width(110.dp)) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(times[index].substringAfter("T"))
                                        Text(weather.icon, fontSize = 28.sp)
                                        Text("${temperatures[index] ?: "—"} °C")
                                    }
                                }
                            }
                        }
                    }

                    val daily = state.forecast.daily

                    if (daily != null) {
                        Text(
                            "7 Günlük Hava Durumu",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        val dates = daily.time.orEmpty()
                        val maxTemperatures = daily.temperatureMax.orEmpty()
                        val minTemperatures = daily.temperatureMin.orEmpty()
                        val rainProbabilities =
                            daily.precipitationProbabilityMax.orEmpty()

                        val dayCount = minOf(
                            7,
                            dates.size,
                            maxTemperatures.size,
                            minTemperatures.size
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (index in 0 until dayCount) {
                                val code = daily.weatherCode
                                    .orEmpty()
                                    .getOrNull(index) ?: -1

                                val weather = getWeatherAppearance(code)

                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            dates[index],
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text("${weather.icon} ${weather.description}")
                                        Text("En yüksek: ${maxTemperatures[index] ?: "—"} °C")
                                        Text("En düşük: ${minTemperatures[index] ?: "—"} °C")
                                        Text(
                                            "Yağış ihtimali: ${rainProbabilities.getOrNull(index) ?: "—"}%"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}