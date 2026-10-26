package com.example.havamuhafeleti

import kotlin.math.roundToInt

data class ActivityOption(
    val name: String,
    val icon: String
)

data class ActivityRating(
    val score: Int,
    val message: String
)

val activityOptions = listOf(
    ActivityOption("Piknik", "🧺"),
    ActivityOption("Yürüyüş", "🚶"),
    ActivityOption("Koşu", "🏃"),
    ActivityOption("Bisiklet", "🚲"),
    ActivityOption("Fotoğraf", "📸")
)

fun calculateActivityRating(
    activity: String,
    temperature: Double,
    windSpeed: Double,
    weatherCode: Int,
    rainProbability: Int
): ActivityRating {

    val rain = rainProbability.coerceIn(0, 100)

    val rainyCodes = listOf(
        51, 53, 55, 56, 57,
        61, 63, 65, 66, 67,
        80, 81, 82, 95, 96, 99
    )

    val snowCodes = listOf(71, 73, 75, 77, 85, 86)

    var score = 100.0

    val idealTemperature = when (activity) {
        "Koşu" -> 15.0
        "Bisiklet" -> 19.0
        "Piknik" -> 23.0
        "Fotoğraf" -> 20.0
        else -> 20.0
    }

    score -= kotlin.math.abs(temperature - idealTemperature) * 2.2

    val rainPenalty = when (activity) {
        "Piknik" -> 0.55
        "Bisiklet" -> 0.50
        "Fotoğraf" -> 0.35
        else -> 0.40
    }

    score -= rain * rainPenalty

    val windPenalty = when (activity) {
        "Bisiklet" -> 0.85
        "Piknik" -> 0.65
        "Koşu" -> 0.45
        else -> 0.35
    }

    score -= (windSpeed - 10.0).coerceAtLeast(0.0) * windPenalty

    if (weatherCode in rainyCodes) {
        score -= 12
    }

    if (weatherCode in snowCodes) {
        score -= 20
    }

    if (weatherCode in listOf(95, 96, 99)) {
        score -= 35
    }

    val finalScore = score.roundToInt().coerceIn(0, 100)

    val message = when {
        weatherCode in listOf(95, 96, 99) ->
            "Gök gürültülü hava nedeniyle açık hava planını ertelemeni öneriyoruz."

        finalScore >= 80 ->
            "Harika! Hava bu etkinlik için oldukça uygun görünüyor."

        finalScore >= 60 ->
            "Plan yapılabilir! Yine de hava koşullarına hazırlıklı ol."

        finalScore >= 40 ->
            "Hava biraz kararsız. Alternatif bir plan düşünmek iyi olabilir."

        else ->
            "Hava bu etkinlik için pek uygun görünmüyor. Başka bir gün deneyebilirsin."
    }

    return ActivityRating(finalScore, message)
}

fun calculateWeatherIndex(
    temperature: Double,
    windSpeed: Double,
    weatherCode: Int,
    rainProbability: Int
): ActivityRating {

    return calculateActivityRating(
        activity = "Yürüyüş",
        temperature = temperature,
        windSpeed = windSpeed,
        weatherCode = weatherCode,
        rainProbability = rainProbability
    )
}