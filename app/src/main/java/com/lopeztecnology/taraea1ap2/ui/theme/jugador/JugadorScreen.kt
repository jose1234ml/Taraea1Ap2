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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun JugadorScreen(
    viewModel: JugadorViewModel,
    navToCrear: () -> Unit,
    navToStartGame: () -> Unit,
    navToHistorial: (String) -> Unit
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
    navToHistorial: (String) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
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
                color = colorScheme.onBackground,
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
                            .clickable { navToHistorial(jugador.nombres) },
                        colors = CardDefaults.cardColors(containerColor = colorScheme.primaryContainer),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${jugador.nombres} • ${jugador.partidas} partidas",
                            color = colorScheme.onPrimaryContainer,
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
                    normalColor = colorScheme.secondary,
                    pressedColor = colorScheme.secondaryContainer,
                    onClick = navToCrear,
                    textColor = colorScheme.onSecondary
                )

                UiComponents.AnimatedButton(
                    text = "Crear Partida",
                    normalColor = colorScheme.primary,
                    pressedColor = colorScheme.primaryContainer,
                    onClick = navToStartGame,
                    textColor = colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            state.error?.let {
                Text(
                    text = it,
                    color = colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            state.successMessage?.let {
                Text(
                    text = it,
                    color = colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
