package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogrosScreen(
    viewModel: JugadorViewModel,
    onJugadorClick: (JugadorLogros) -> Unit,
    navBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val jugadoresLogrosMap by viewModel.jugadoresLogros.collectAsState()

    val jugadoresLogros = state.jugadores.map { jugador ->
        val logros = jugadoresLogrosMap[jugador.jugadorId]?.map { it.descripcion } ?: emptyList()
        JugadorLogros(
            jugador = jugador,
            cantidadLogros = logros.size,
            logros = logros
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Logros de Jugadores",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(jugadoresLogros) { jugadorLogros ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onJugadorClick(jugadorLogros) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = jugadorLogros.jugador.nombres,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "${jugadorLogros.cantidadLogros}",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            jugadorLogros.logros.forEach { logro ->
                                Text(
                                    text = "• $logro",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = navBack, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Volver")
            }
        }
    }
}
