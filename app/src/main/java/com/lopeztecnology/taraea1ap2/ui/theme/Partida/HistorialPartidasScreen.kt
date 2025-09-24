package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import com.lopeztecnology.taraea1ap2.data.local.PartidaEntity

@Composable
fun HistorialPartidasScreen(
    viewModel: PartidaViewModel,
    navBack: () -> Unit,
    navToPartida: (PartidaEntity) -> Unit,
    navToCrearPartida: () -> Unit
) {

    val partidas by viewModel.obtenerTodasLasPartidas()
        .collectAsState(initial = emptyList())

    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Partidas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(partidas) { partida ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { navToPartida(partida) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "ID: ${partida.partidaId}",
                                color = colorScheme.onPrimaryContainer,
                                fontSize = 14.sp
                            )
                            Text(
                                "Jugador X: ${partida.jugadorX ?: "Desconocido"}",
                                color = colorScheme.onPrimaryContainer,
                                fontSize = 16.sp
                            )
                            Text(
                                "Jugador O: ${partida.jugadorO ?: "Desconocido"}",
                                color = colorScheme.onPrimaryContainer,
                                fontSize = 16.sp
                            )
                            Text(
                                "Ganador: ${partida.ganador ?: "Empate"}",
                                color = colorScheme.tertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                "Finalizada: ${if (partida.esFinalizada == true) "Sí" else "No"}",
                                color = colorScheme.onPrimaryContainer,
                                fontSize = 14.sp
                            )
                            val fechaTexto = try {
                                SimpleDateFormat(
                                    "dd/MM/yyyy HH:mm:ss",
                                    Locale.getDefault()
                                ).format(Date(partida.fecha ?: 0))
                            } catch (_: Exception) {
                                "Desconocida"
                            }
                            Text(
                                "Fecha: $fechaTexto",
                                color = colorScheme.onPrimaryContainer,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            UiComponents.AnimatedButton(
                text = "Volver",
                normalColor = colorScheme.error,
                pressedColor = colorScheme.errorContainer,
                textColor = colorScheme.onError,
                onClick = navBack
            )
        }


        FloatingActionButton(
            onClick = navToCrearPartida,
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 100.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Crear Partida")
        }
    }
}