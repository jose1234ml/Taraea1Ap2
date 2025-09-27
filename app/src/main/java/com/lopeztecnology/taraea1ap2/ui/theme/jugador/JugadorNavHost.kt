package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*

@Composable
fun JugadorNavHost(
    viewModel: JugadorViewModel,
    partidaViewModel: PartidaViewModel
) {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController = navController, startDestination = "main") {

            composable("main") {
                MainScreen(
                    navToCrearJugador = { navController.navigate("crearJugador") },
                    navToCrearPartida = { navController.navigate("startGame") },
                    navToHistorial = { navController.navigate("historial") },
                    navToLogros = { navController.navigate("logros") }
                )
            }

            composable("crearJugador") {
                JugadorCrearScreen(
                    viewModel = viewModel,
                    navBack = { navController.popBackStack() }
                )
            }

            composable("startGame") {
                StartGameScreen(
                    jugadores = viewModel.state.value.jugadores,
                    navBack = { navController.popBackStack() },
                    onStartGame = { jugadorX, jugadorO ->
                        partidaViewModel.iniciarPartida(jugadorX, jugadorO)
                        navController.navigate("partida")
                    }
                )
            }

            composable("partida") {
                PartidaScreen(
                    viewModel = partidaViewModel,
                    navBack = { navController.popBackStack() }
                )
            }

            composable("historial") {
                HistorialPartidasScreen(
                    viewModel = partidaViewModel,
                    navBack = { navController.popBackStack() },
                    navToPartida = { partidaEntity ->
                        partidaViewModel.cargarPartidaSeleccionada(partidaEntity)
                        navController.navigate("partida")
                    },
                    navToCrearPartida = { navController.navigate("startGame") }
                )
            }

            // Nueva pantalla de logros
            composable("logros") {
                LogrosScreen(
                    viewModel = viewModel,
                    onJugadorClick = { jugadorLogros ->
                        partidaViewModel.seleccionarJugadorLogros(jugadorLogros)
                        navController.navigate("detalleLogros")
                    },
                    navBack = { navController.popBackStack() }
                )
            }

            composable("detalleLogros") {
                val jugadorLogros = partidaViewModel.jugadorSeleccionado.value
                if (jugadorLogros != null) {
                    JugadorDetalleLogrosScreen(
                        jugadorLogros = jugadorLogros,
                        navBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    navToCrearJugador: () -> Unit,
    navToCrearPartida: () -> Unit,
    navToHistorial: () -> Unit,
    navToLogros: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Pantalla Principal",
                fontSize = 28.sp,
                color = colorScheme.primary
            )

            Button(onClick = navToCrearJugador, modifier = Modifier.fillMaxWidth()) {
                Text("Crear Jugador")
            }

            Button(onClick = navToCrearPartida, modifier = Modifier.fillMaxWidth()) {
                Text("Crear Partida")
            }

            Button(onClick = navToHistorial, modifier = Modifier.fillMaxWidth()) {
                Text("Historial de Partidas")
            }

            Button(onClick = navToLogros, modifier = Modifier.fillMaxWidth()) {
                Text("Logros")
            }
        }
    }
}
