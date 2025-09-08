package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun JugadorCrearScreen(viewModel: JugadorViewModel, navBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D47A1))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { viewModel.onEvent(JugadorEvent.NombreChanged(it)) },
                label = { Text("Nombre completo", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.partidas,
                onValueChange = { viewModel.onEvent(JugadorEvent.PartidasChanged(it)) },
                label = { Text("Partidas (número)", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.onEvent(JugadorEvent.GuardarJugador)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text("Guardar", color = Color.Black)
                }

                OutlinedButton(
                    onClick = { viewModel.onEvent(JugadorEvent.ClearMessages) },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFFC107))
                ) {
                    Text("Limpiar mensajes")
                }
            }

            Button(
                onClick = navBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al listado de jugadores", color = Color.Black)
            }

            state.error?.let { Text(it, color = Color.Red, fontWeight = FontWeight.Bold) }
            state.successMessage?.let { Text(it, color = Color.Green, fontWeight = FontWeight.Bold) }
        }
    }
}
