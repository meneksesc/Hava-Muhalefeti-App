package com.example.havamuhafeleti

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun getWeatherBackground(code: Int): Brush {

    val colors = when (code) {

        0, 1 -> listOf(
            Color(0xFF4A90E2),
            Color(0xFF87CEFA),
            Color(0xFFFFD89B)
        )

        2, 3 -> listOf(
            Color(0xFF536976),
            Color(0xFF8DA9B8),
            Color(0xFFD7E3EA)
        )

        45, 48 -> listOf(
            Color(0xFF667781),
            Color(0xFFB0BEC5)
        )

        51, 53, 55, 56, 57,
        61, 63, 65, 66, 67,
        80, 81, 82,
        95, 96, 99 -> listOf(
            Color(0xFF203A59),
            Color(0xFF4C6B8A),
            Color(0xFF8BA8C2)
        )

        71, 73, 75, 77, 85, 86 -> listOf(
            Color(0xFF91B7D4),
            Color(0xFFD9EAF5),
            Color(0xFFF5FAFF)
        )

        else -> listOf(
            Color(0xFF547AA5),
            Color(0xFFB7D3E8)
        )
    }

    return Brush.verticalGradient(colors)
}