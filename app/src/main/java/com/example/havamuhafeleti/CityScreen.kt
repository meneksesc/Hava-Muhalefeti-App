package com.example.havamuhafeleti

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

private val homeGradient = Brush.verticalGradient(
    listOf(
        Color(0xFF17365D),
        Color(0xFF4779AD),
        Color(0xFFA8C9E8)
    )
)

@Composable
fun CityCard(
    city: City,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.90f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = city.name,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF17365D)
                )

                Text(
                    text = city.subtitle,
                    fontSize = 13.sp,
                    color = Color(0xFF58718C)
                )
            }

            Text(
                text = "→",
                fontSize = 25.sp,
                color = Color(0xFF4779AD)
            )
        }
    }
}

@Composable
fun CityScreen(
    cityViewModel: CityViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel(),
    onCitySelected: (City) -> Unit = {}
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }

    val uiState by cityViewModel.uiState.collectAsStateWithLifecycle()

    val favoriteCities by favoritesViewModel.favoriteCities
        .collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(homeGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "☁️",
                fontSize = 48.sp
            )

            Text(
                text = "Hava Muhalefeti",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Hava nasıl olursa olsun,\nplanların hazır olsun.",
                fontSize = 17.sp,
                lineHeight = 24.sp,
                color = Color.White.copy(alpha = 0.88f)
            )

            OutlinedTextField(
                value = query,
                onValueChange = { newText ->
                    query = newText
                    cityViewModel.searchCities(newText)
                },
                label = {
                    Text("Hangi şehrin havasına bakalım?")
                },
                leadingIcon = {
                    Text("🔍")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White.copy(alpha = 0.94f),
                    focusedTextColor = Color(0xFF17365D),
                    unfocusedTextColor = Color(0xFF17365D),
                    focusedLabelColor = Color(0xFF17365D),
                    unfocusedLabelColor = Color(0xFF58718C)
                )
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (favoriteCities.isNotEmpty()) {
                    item {
                        Text(
                            text = "❤️ Favori Şehirlerim",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    items(
                        items = favoriteCities,
                        key = { "favorite_${it.id}" }
                    ) { city ->
                        CityCard(
                            city = city,
                            onClick = { onCitySelected(city) }
                        )
                    }
                }

                when (val state = uiState) {
                    CityUiState.Idle -> {
                        item {
                            Text(
                                text = if (favoriteCities.isEmpty()) {
                                    "🌍 Dünyanın her yerinden bir şehir keşfet!"
                                } else {
                                    "🌍 Yeni bir şehir keşfetmek için arama yap."
                                },
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }

                    CityUiState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        }
                    }

                    is CityUiState.Success -> {
                        item {
                            Text(
                                text = "📍 Arama Sonuçları",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        items(
                            items = state.cities,
                            key = { "search_${it.id}" }
                        ) { city ->
                            CityCard(
                                city = city,
                                onClick = { onCitySelected(city) }
                            )
                        }
                    }

                    CityUiState.Empty -> {
                        item {
                            Text(
                                "Şehir bulunamadı. Başka bir isim dene.",
                                color = Color.White
                            )
                        }
                    }

                    is CityUiState.Error -> {
                        item {
                            Text(
                                "Bir sorun oluştu: ${state.message}",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}