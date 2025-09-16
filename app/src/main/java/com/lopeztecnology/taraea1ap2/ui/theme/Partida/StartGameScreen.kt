package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lopeztecnology.taraea1ap2.data.local.Jugador

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartGameScreen(
    jugadores: List<Jugador>,
    navBack: () -> Unit,
    onStartGame: (Jugador, Jugador) -> Unit
) {
    var jugadorX by remember { mutableStateOf<Jugador?>(null) }
    var jugadorO by remember { mutableStateOf<Jugador?>(null) }

    var showSheetX by remember { mutableStateOf(false) }
    var showSheetO by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D47A1))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {


            Text(
                text = "Seleccionar Jugadores",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            UiComponents.AnimatedButton(
                text = jugadorX?.nombres ?: "Seleccionar Jugador X",
                normalColor = Color(0xFF4CAF50),
                pressedColor = Color(0xFF81C784),
                textColor = Color.White,
                onClick = { showSheetX = true }
            )


            UiComponents.AnimatedButton(
                text = jugadorO?.nombres ?: "Seleccionar Jugador O",
                normalColor = Color(0xFF4CAF50),
                pressedColor = Color(0xFF81C784),
                textColor = Color.White,
                onClick = { showSheetO = true }
            )


            UiComponents.AnimatedButton(
                text = "Iniciar Partida",
                normalColor = if (jugadorX != null && jugadorO != null) Color(0xFFFFC107) else Color.Gray,
                pressedColor = if (jugadorX != null && jugadorO != null) Color(0xFFFFD740) else Color.Gray,
                textColor = Color.Black,
                onClick = { if (jugadorX != null && jugadorO != null) onStartGame(jugadorX!!, jugadorO!!) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Volver
            UiComponents.AnimatedButton(
                text = "Volver",
                normalColor = Color(0xFFE53935),
                pressedColor = Color(0xFFEF5350),
                textColor = Color.White,
                onClick = navBack
            )
        }
    }


    if (showSheetX) {
        ModalBottomSheet(onDismissRequest = { showSheetX = false }) {
            LazyColumn {
                items(jugadores) { jugador ->
                    Text(
                        text = jugador.nombres,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                jugadorX = jugador
                                showSheetX = false
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }


    if (showSheetO) {
        ModalBottomSheet(onDismissRequest = { showSheetO = false }) {
            LazyColumn {
                items(jugadores) { jugador ->
                    Text(
                        text = jugador.nombres,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                jugadorO = jugador
                                showSheetO = false
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartGameScreenPreview() {
    val jugadoresFake = listOf(
        Jugador(jugadorId = 1, nombres = "Juan Pérez", partidas = 5),
        Jugador(jugadorId = 2, nombres = "Ana López", partidas = 3),
        Jugador(jugadorId = 3, nombres = "Carlos Gómez", partidas = 7)
    )

    StartGameScreen(
        jugadores = jugadoresFake,
        navBack = {},
        onStartGame = { _, _ -> }
    )
}

