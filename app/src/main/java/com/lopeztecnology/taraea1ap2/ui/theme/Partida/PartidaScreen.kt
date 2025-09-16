package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight


@Composable
fun PartidaScreen(
    viewModel: PartidaViewModel,
    navBack: () -> Unit
) {
    val partida = viewModel.partida ?: return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D47A1))
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
                color = Color.White
            )


            Column {
                partida.tablero.forEachIndexed { filaIndex, fila ->
                    Row {
                        fila.forEachIndexed { colIndex, celda ->
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                                    .clickable { viewModel.jugar(filaIndex, colIndex) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = celda,
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (celda == "X") Color.Red else Color.Blue
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
                    color = Color.Yellow
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            UiComponents.AnimatedButton(
                text = "Reiniciar Partida",
                normalColor = Color(0xFFFFC107),
                pressedColor = Color(0xFFFFD740),
                textColor = Color.Black,
                onClick = { viewModel.reiniciar() }
            )

            UiComponents.AnimatedButton(
                text = "Volver",
                normalColor = Color(0xFFE53935),
                pressedColor = Color(0xFFEF5350),
                textColor = Color.White,
                onClick = navBack
            )
        }
    }
}
