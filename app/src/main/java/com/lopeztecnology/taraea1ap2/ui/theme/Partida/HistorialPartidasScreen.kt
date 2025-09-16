package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lopeztecnology.taraea1ap2.data.local.PartidaEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistorialPartidasScreen(
    nombreJugador: String,
    viewModel: PartidaViewModel,
    navBack: () -> Unit
) {
    val partidas by viewModel.obtenerPartidasPorJugador(nombreJugador).collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D47A1))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Historial de partidas de $nombreJugador",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                items(partidas) { partida ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1565C0))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("ID: ${partida.partidaId}", color = Color.White, fontSize = 14.sp)
                            Text("Jugador X: ${partida.jugadorX}", color = Color.White, fontSize = 16.sp)
                            Text("Jugador O: ${partida.jugadorO}", color = Color.White, fontSize = 16.sp)
                            Text(
                                "Ganador: ${partida.ganador ?: "Empate"}",
                                color = Color.Yellow,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text("Finalizada: ${partida.esFinalizada}", color = Color.White, fontSize = 14.sp)
                            Text(
                                "Fecha: ${
                                    SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                                        .format(Date(partida.fecha))
                                }",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
