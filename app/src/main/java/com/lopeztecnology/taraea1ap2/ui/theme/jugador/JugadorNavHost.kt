package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.runtime.Composable
import androidx.navigation.NavType

import androidx.navigation.compose.*
import androidx.navigation.navArgument

@Composable
fun JugadorNavHost(
    viewModel: JugadorViewModel,
    partidaViewModel: PartidaViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "listado") {

        composable("crear") {
            JugadorCrearScreen(
                viewModel = viewModel,
                navBack = { navController.navigate("listado") { popUpTo("crear") { inclusive = true } } }
            )
        }

        composable("listado") {
            JugadorScreen(
                viewModel = viewModel,
                navToCrear = { navController.navigate("crear") },
                navToStartGame = { navController.navigate("startGame") },
                navToHistorial = { nombreJugador ->
                    navController.navigate("historial/$nombreJugador")
                }
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


        composable(
            route = "historial/{nombreJugador}",
            arguments = listOf(navArgument("nombreJugador") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombreJugador = backStackEntry.arguments?.getString("nombreJugador") ?: ""
            HistorialPartidasScreen(
                nombreJugador = nombreJugador,
                viewModel = partidaViewModel,
                navBack = { navController.popBackStack() }
            )
        }
    }
}
