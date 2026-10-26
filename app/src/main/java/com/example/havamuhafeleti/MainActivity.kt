package com.example.havamuhafeleti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.havamuhafeleti.ui.theme.HavaMuhafeletiTheme
import kotlinx.coroutines.delay
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HavaMuhafeletiTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    var showWelcome by rememberSaveable {
        mutableStateOf(true)
    }

    var selectedCity by remember {
        mutableStateOf<City?>(null)
    }

    LaunchedEffect(Unit) {
        delay(1500)
        showWelcome = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (showWelcome) {
            WelcomeScreen()
        } else {
            val city = selectedCity

            if (city == null) {
                CityScreen(
                    onCitySelected = { selected ->
                        selectedCity = selected
                    }
                )
            } else {
                ForecastScreen(
                    city = city,
                    onBack = {
                        selectedCity = null
                    }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF102D50),
            Color(0xFF3D76B0),
            Color(0xFFA8C9E8)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "🌤️",
                fontSize = 80.sp
            )

            Text(
                text = "Hava Muhalefeti",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Hava değişir, planlar değişmez.",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}