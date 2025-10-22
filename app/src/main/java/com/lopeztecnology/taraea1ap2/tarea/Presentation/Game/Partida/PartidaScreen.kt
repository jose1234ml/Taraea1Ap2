package com.lopeztecnology.taraea1ap2.tarea.Presentation.Game.Partida

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lopeztecnology.taraea1ap2.domain.model.Jugador


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidaScreen(viewModel: GameViewModel = hiltViewModel()) {
    val isGameStarted by viewModel.isGameStarted.collectAsStateWithLifecycle()
    val partidaId by viewModel.partidaId.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Juego ") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {

                if (isGameStarted) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = partidaId,
                            onValueChange = { viewModel.setPartidaId(it) },
                            label = { Text("ID Partida") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                .clickable { viewModel.cargarPartidaPorId() },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("⟳", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }


                if (!isGameStarted) {
                    PartidaSetupScreen(viewModel)
                } else {
                    val gameState by viewModel.gameState.collectAsStateWithLifecycle()
                    GameBoard(
                        uiState = gameState,
                        onCellClick = viewModel::onCellClick,
                        onRestartGame = viewModel::restartGame,
                        onExitGame = viewModel::volverASeleccionDeJugador,
                        jugadorX = viewModel.setupState.value.jugadorIzquierda?.nombres ?: "Jugador X",
                        jugadorO = viewModel.setupState.value.jugadorDerecha?.nombres ?: "Jugador O"
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidaSetupScreen(viewModel: GameViewModel) {
    val state by viewModel.setupState.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectingLeft by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                "Selecciona jugadores",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlayerSlotBasic(
                    jugador = state.jugadorIzquierda,
                    placeholder = "Jugador X",
                    onClick = {
                        selectingLeft = true
                        showBottomSheet = true
                    }
                )
                Text("VS", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                PlayerSlotBasic(
                    jugador = state.jugadorDerecha,
                    placeholder = "Jugador O",
                    onClick = {
                        selectingLeft = false
                        showBottomSheet = true
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { viewModel.startGame() },
                enabled = state.jugadorIzquierda != null && state.jugadorDerecha != null
            ) {
                Text("Iniciar partida")
            }
        }
    }

    if (showBottomSheet) {
        val listaParaMostrar = if (selectingLeft) {
            state.listaOponentes.filter { it.jugadorId != state.jugadorDerecha?.jugadorId }
        } else {
            state.listaOponentes.filter { it.jugadorId != state.jugadorIzquierda?.jugadorId }
        }

        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Text(
                        text = "Selecciona un oponente",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                items(listaParaMostrar) { jugador ->
                    Text(
                        text = jugador.nombres,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (selectingLeft) viewModel.onJugadorIzquierdaSelected(jugador)
                                else viewModel.onJugadorDerechaSelected(jugador)
                                showBottomSheet = false
                            }
                            .padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun PlayerSlotBasic(jugador: Jugador?, placeholder: String, onClick: (() -> Unit)? = null) {
    Surface(
        modifier = Modifier
            .size(100.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = jugador?.nombres ?: placeholder,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun GameBoard(
    uiState: GameUiState,
    onCellClick: (Int) -> Unit,
    onRestartGame: () -> Unit,
    onExitGame: () -> Unit,
    jugadorX: String,
    jugadorO: String
) {
    val gameStatus = when {
        uiState.winner == Player.X -> "$jugadorX ganó"
        uiState.winner == Player.O -> "$jugadorO ganó"
        uiState.isDraw -> "Empate"
        uiState.currentPlayer == Player.X -> "Turno de $jugadorX"
        else -> "Turno de $jugadorO"
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = gameStatus,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            (0..2).forEach { row ->
                Row {
                    (0..2).forEach { col ->
                        val index = row * 3 + col
                        BoardCellBasic(uiState.board[index]) { onCellClick(index) }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onRestartGame) { Text("Nuevo Juego") }
            Button(onClick = onExitGame) { Text("Salir") }
        }
    }
}


@Composable
fun BoardCellBasic(player: Player?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player?.Symbol ?: "",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
