package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun PartidaScreen(
    viewModel: PartidaViewModel,
    navBack: () -> Unit
) {
    val partida = viewModel.partida ?: return
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Turno: ${partida.turno}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onBackground
            )

            Column {
                partida.tablero.forEachIndexed { filaIndex, fila ->
                    Row {
                        fila.forEachIndexed { colIndex, celda ->
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(
                                        color = colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { viewModel.jugar(filaIndex, colIndex) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = celda,
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (celda) {
                                        "X" -> colorScheme.error
                                        "O" -> colorScheme.primary
                                        else -> colorScheme.onSurfaceVariant // texto gris oscuro
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            partida.ganador?.let {
                Text(
                    text = if (it == "Empate") "Empate!" else "Ganador: $it",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.tertiary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            UiComponents.AnimatedButton(
                text = "Reiniciar Partida",
                normalColor = colorScheme.secondary,
                pressedColor = colorScheme.secondaryContainer,
                textColor = colorScheme.onSecondary,
                onClick = { viewModel.reiniciar() }
            )

            UiComponents.AnimatedButton(
                text = "Volver",
                normalColor = colorScheme.error,
                pressedColor = colorScheme.errorContainer,
                textColor = colorScheme.onError,
                onClick = navBack
            )
        }
    }
}