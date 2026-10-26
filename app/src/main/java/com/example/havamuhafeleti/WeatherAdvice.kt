package com.example.havamuhafeleti

data class WeatherAdvice(
    val icon: String,
    val message: String
)

fun getWeatherAdvice(
    weatherCode: Int,
    temperature: Double,
    windSpeed: Double
): List<WeatherAdvice> {

    val advice = mutableListOf<WeatherAdvice>()

    if (weatherCode in listOf(
            51, 53, 55, 56, 57,
            61, 63, 65, 66, 67,
            80, 81, 82, 95, 96, 99
        )
    ) {
        advice.add(
            WeatherAdvice(
                "☂️",
                "Şemsiyeni almayı unutma! Bugün yağışlı bir hava var."
            )
        )
    }

    if (weatherCode in listOf(71, 73, 75, 77, 85, 86)) {
        advice.add(
            WeatherAdvice(
                "❄️",
                "Kar yağışı var! Kalın giyin ve kaygan zeminlere dikkat et."
            )
        )
    }

    if (windSpeed >= 25) {
        advice.add(
            WeatherAdvice(
                "💨",
                "Rüzgâr güçlü! Etek yerine pantolon tercih edebilirsin."
            )
        )
    }

    if (temperature >= 30) {
        advice.add(
            WeatherAdvice(
                "🥵",
                "Hava çok sıcak! Bol su iç, güneş altında uzun süre kalma."
            )
        )
    }

    if (temperature <= 5) {
        advice.add(
            WeatherAdvice(
                "🧣",
                "Hava çok soğuk! Atkı, eldiven ve kalın montunu unutma."
            )
        )
    } else if (temperature <= 15) {
        advice.add(
            WeatherAdvice(
                "🧥",
                "Hava serin, yanına bir ceket alman iyi olur."
            )
        )
    }

    if (weatherCode in listOf(0, 1) && temperature >= 20) {
        advice.add(
            WeatherAdvice(
                "🕶️",
                "Güneş gözlüğünü ve güneş kremini unutma!"
            )
        )
    }

    if (weatherCode in listOf(45, 48)) {
        advice.add(
            WeatherAdvice(
                "🌫️",
                "Sis nedeniyle görüş mesafesi azalabilir. Dikkatli ol!"
            )
        )
    }

    if (advice.isEmpty()) {
        advice.add(
            WeatherAdvice(
                "🌿",
                "Bugün hava oldukça uygun görünüyor. Günün tadını çıkar!"
            )
        )
    }

    return advice
}