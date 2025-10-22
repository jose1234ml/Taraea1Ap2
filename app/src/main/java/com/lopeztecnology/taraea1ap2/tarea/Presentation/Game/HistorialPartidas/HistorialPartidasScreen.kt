package com.lopeztecnology.taraea1ap2.tarea.Presentation.Game.HistorialPartidas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.lopeztecnology.taraea1ap2.domain.model.Partida
import com.lopeztecnology.taraea1ap2.tarea.Presentation.Game.Partida.Player

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialPartidasScreen(
    navigation: NavController,
    viewModel: HistorialPartidasViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MaterialTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Historial de Partidas") }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { navigation.navigate("partida_screen") }) {
                    Icon(Icons.Default.Add, contentDescription = "Nueva partida")
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    state.partidas.isEmpty() -> Text(
                        "No hay partidas registradas.",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp
                    )
                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.partidas, key = { it.partida.partidaId }) { partidaConGanador ->
                            PartidaItemBasic(partidaConGanador)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PartidaItemBasic(
    partidaConGanador: PartidaConGanador,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Partida #${partidaConGanador.partida.partidaId}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fecha: ${partidaConGanador.partida.fecha}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            val resultado = when {
                partidaConGanador.nombreGanador != null -> "Ganador: ${partidaConGanador.nombreGanador}"
                partidaConGanador.partida.esFinalizada -> "Empate"
                else -> "En curso"
            }
            Text(
                text = resultado,
                fontSize = 16.sp,
                color = if (partidaConGanador.nombreGanador != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
