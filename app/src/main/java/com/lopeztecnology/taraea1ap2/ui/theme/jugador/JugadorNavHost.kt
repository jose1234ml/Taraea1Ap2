package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

@Composable
fun JugadorNavHost(viewModel: JugadorViewModel) {
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
                navToCrear = { navController.navigate("crear") }
            )
        }
    }
}
