package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun JugadorCrearScreen(
    viewModel: JugadorViewModel,
    navBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    JugadorCrearContent(
        state = state,
        onEvent = { viewModel.onEvent(it) },
        navBack = navBack
    )
}

@Composable
fun JugadorCrearContent(
    state: JugadorState,
    onEvent: (JugadorEvent) -> Unit,
    navBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(JugadorEvent.NombreChanged(it)) },
                label = { Text("Nombre completo") },
                textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorScheme.onBackground,
                    unfocusedTextColor = colorScheme.onBackground,
                    focusedLabelColor = colorScheme.primary,
                    unfocusedLabelColor = colorScheme.onBackground,
                    focusedBorderColor = colorScheme.primary,
                    unfocusedBorderColor = colorScheme.outline
                )
            )

            OutlinedTextField(
                value = state.partidas,
                onValueChange = { onEvent(JugadorEvent.PartidasChanged(it)) },
                label = { Text("Partidas (número)") },
                textStyle = LocalTextStyle.current.copy(color = colorScheme.onBackground),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorScheme.onBackground,
                    unfocusedTextColor = colorScheme.onBackground,
                    focusedLabelColor = colorScheme.primary,
                    unfocusedLabelColor = colorScheme.onBackground,
                    focusedBorderColor = colorScheme.primary,
                    unfocusedBorderColor = colorScheme.outline
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(JugadorEvent.GuardarJugador)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary)
                ) {
                    Text("Guardar", color = colorScheme.onPrimary)
                }

                OutlinedButton(
                    onClick = { onEvent(JugadorEvent.ClearMessages) },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = colorScheme.secondary)
                ) {
                    Text("Limpiar mensajes")
                }
            }

            Button(
                onClick = navBack,
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al listado de jugadores", color = colorScheme.onSecondary)
            }

            state.error?.let {
                Text(it, color = colorScheme.error, fontWeight = FontWeight.Bold)
            }
            state.successMessage?.let {
                Text(it, color = colorScheme.tertiary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JugadorCrearScreenPreview() {
    val fakeState = JugadorState(
        nombre = "Juan Pérez",
        partidas = "5",
        error = null,
        successMessage = "Jugador guardado con éxito"
    )

    MaterialTheme {
        JugadorCrearContent(
            state = fakeState,
            onEvent = {},
            navBack = {}
        )
    }
}