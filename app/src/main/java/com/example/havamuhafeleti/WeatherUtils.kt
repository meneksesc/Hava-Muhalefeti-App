package com.example.havamuhafeleti

data class WeatherAppearance(
    val description: String,
    val icon: String
)

fun getWeatherAppearance(code: Int): WeatherAppearance {
    return when (code) {
        0 -> WeatherAppearance("Açık", "☀️")
        1 -> WeatherAppearance("Çoğunlukla açık", "🌤️")
        2 -> WeatherAppearance("Parçalı bulutlu", "⛅")
        3 -> WeatherAppearance("Kapalı", "☁️")

        45, 48 -> WeatherAppearance("Sisli", "🌫️")

        51, 53, 55 -> WeatherAppearance("Çiseleme", "🌦️")
        56, 57 -> WeatherAppearance("Dondurucu çiseleme", "🌧️")

        61, 63, 65 -> WeatherAppearance("Yağmurlu", "🌧️")
        66, 67 -> WeatherAppearance("Dondurucu yağmur", "🌧️")

        71, 73, 75 -> WeatherAppearance("Karlı", "❄️")
        77 -> WeatherAppearance("Kar taneleri", "❄️")

        80, 81, 82 -> WeatherAppearance("Sağanak yağışlı", "🌧️")
        85, 86 -> WeatherAppearance("Kar sağanakları", "🌨️")

        95 -> WeatherAppearance("Gök gürültülü", "⛈️")
        96, 99 -> WeatherAppearance("Dolu ihtimalli fırtına", "⛈️")

        else -> WeatherAppearance("Bilinmeyen hava durumu", "🌡️")
    }
}