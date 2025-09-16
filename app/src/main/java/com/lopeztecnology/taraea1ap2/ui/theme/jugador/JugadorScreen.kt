package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JugadorScreen(
    viewModel: JugadorViewModel,
    navToCrear: () -> Unit,
    navToStartGame: () -> Unit,
    navToHistorial: (String) -> Unit  // ✅ Nueva lambda
) {
    val state by viewModel.state.collectAsState()
    JugadorScreenContent(
        state = state,
        onEvent = { viewModel.onEvent(it) },
        navToCrear = navToCrear,
        navToStartGame = navToStartGame,
        navToHistorial = navToHistorial
    )
}

@Composable
fun JugadorScreenContent(
    state: JugadorState,
    onEvent: (JugadorEvent) -> Unit,
    navToCrear: () -> Unit,
    navToStartGame: () -> Unit,
    navToHistorial: (String) -> Unit  // ✅ Nueva lambda
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D47A1))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Jugadores Registrados",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(state.jugadores) { jugador ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { navToHistorial(jugador.nombres) }, // ✅ Click para ver historial
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1565C0)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${jugador.nombres} • ${jugador.partidas} partidas",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                UiComponents.AnimatedButton(
                    text = "Crear Nuevo Jugador",
                    normalColor = Color(0xFFFFC107),
                    pressedColor = Color(0xFFFFD740),
                    onClick = navToCrear,
                    textColor = Color.Black
                )

                UiComponents.AnimatedButton(
                    text = "Crear Partida",
                    normalColor = Color(0xFF4CAF50),
                    pressedColor = Color(0xFF81C784),
                    onClick = navToStartGame,
                    textColor = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            state.error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            state.successMessage?.let {
                Text(
                    text = it,
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
