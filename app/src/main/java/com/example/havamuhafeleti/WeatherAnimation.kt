package com.example.havamuhafeleti

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.sin

@Composable
fun WeatherAnimation(weatherCode: Int) {

    val transition = rememberInfiniteTransition(
        label = "weatherAnimation"
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 7000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "weatherProgress"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {

        val width = size.width
        val height = size.height

        val rainy = weatherCode in listOf(
            51, 53, 55, 56, 57,
            61, 63, 65, 66, 67,
            80, 81, 82, 95, 96, 99
        )

        val snowy = weatherCode in listOf(
            71, 73, 75, 77, 85, 86
        )

        val cloudy = weatherCode in listOf(
            1, 2, 3, 45, 48
        ) || rainy || snowy

        // Hareketli bulutlar
        if (cloudy) {
            for (i in 0 until 5) {

                val cloudX = (
                        (i * 0.29f + progress * 0.12f) % 1.4f
                        ) * width - width * 0.2f

                val cloudY = height * (0.12f + i * 0.13f)

                val radius = width * (0.13f + i % 2 * 0.04f)

                drawCircle(
                    color = Color.White.copy(alpha = 0.15f),
                    radius = radius,
                    center = Offset(cloudX, cloudY)
                )

                drawCircle(
                    color = Color.White.copy(alpha = 0.12f),
                    radius = radius * 0.75f,
                    center = Offset(
                        cloudX + radius * 0.8f,
                        cloudY + radius * 0.15f
                    )
                )
            }
        }

        // Yağmur damlaları
        if (rainy) {
            for (i in 0 until 90) {

                val x = ((i * 0.618f) % 1f) * width

                val y = (
                        (i * 0.173f + progress * 3f) % 1f
                        ) * height

                drawLine(
                    color = Color.White.copy(alpha = 0.5f),
                    start = Offset(x, y),
                    end = Offset(x - 6f, y + 22f),
                    strokeWidth = 2f
                )
            }
        }

        // Kar taneleri
        if (snowy) {
            for (i in 0 until 65) {

                val x = (
                        (i * 0.618f +
                                sin(progress * 6.28f + i).toFloat() * 0.02f
                                ) % 1f
                        ) * width

                val y = (
                        (i * 0.137f + progress * 0.6f) % 1f
                        ) * height

                drawCircle(
                    color = Color.White.copy(alpha = 0.8f),
                    radius = 2f + i % 4,
                    center = Offset(x, y)
                )
            }
        }

        // Açık havada güneş parıltısı
        if (weatherCode == 0 || weatherCode == 1) {

            val sunCenter = Offset(
                width * 0.78f,
                height * 0.18f
            )

            drawCircle(
                color = Color(0xFFFFE082).copy(alpha = 0.18f),
                radius = width * (0.2f + progress * 0.03f),
                center = sunCenter
            )

            drawCircle(
                color = Color(0xFFFFF3C4).copy(alpha = 0.85f),
                radius = width * 0.095f,
                center = sunCenter
            )

            drawCircle(
                color = Color.White.copy(alpha = 0.25f),
                radius = width * 0.12f,
                center = sunCenter,
                style = Stroke(width = 3f)
            )
        }
    }
}