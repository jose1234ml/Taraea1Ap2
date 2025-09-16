package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lopeztecnology.taraea1ap2.data.local.Jugador
import com.lopeztecnology.taraea1ap2.data.local.PartidaEntity
import com.lopeztecnology.taraea1ap2.data.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

data class Partida(
    val jugadorX: String,
    val jugadorO: String,
    var turno: String = "X",
    val tablero: Array<Array<String>> = Array(3) { Array(3) { "" } },
    var ganador: String? = null
)

class PartidaViewModel(
    private val repository: PartidaRepository
) : ViewModel() {

    var partida by mutableStateOf<Partida?>(null)
        private set

    fun iniciarPartida(jugadorX: Jugador, jugadorO: Jugador) {
        partida = Partida(jugadorX = jugadorX.nombres, jugadorO = jugadorO.nombres)
    }

    fun jugar(fila: Int, col: Int) {
        val p = partida ?: return
        if (p.tablero[fila][col].isEmpty() && p.ganador == null) {
            p.tablero[fila][col] = p.turno

            if (verificarGanador(p.tablero, p.turno)) {
                p.ganador = p.turno
                guardarPartida(p)
            } else if (tableroLleno(p.tablero)) {
                p.ganador = "Empate"
                guardarPartida(p)
            } else {
                p.turno = if (p.turno == "X") "O" else "X"
            }

            // Forzar recomposición
            val nuevoTablero = Array(3) { filaIndex -> p.tablero[filaIndex].copyOf() }
            partida = p.copy(tablero = nuevoTablero)
        }
    }

    private fun tableroLleno(tablero: Array<Array<String>>): Boolean {
        return tablero.all { fila -> fila.all { it.isNotEmpty() } }
    }

    private fun verificarGanador(tablero: Array<Array<String>>, jugador: String): Boolean {
        if (tablero.any { fila -> fila.all { it == jugador } }) return true
        for (i in 0..2) if ((0..2).all { tablero[it][i] == jugador }) return true
        if ((0..2).all { tablero[it][it] == jugador }) return true
        if ((0..2).all { tablero[it][2 - it] == jugador }) return true
        return false
    }

    private fun guardarPartida(p: Partida) {
        viewModelScope.launch {
            repository.guardarPartida(
                PartidaEntity(
                    jugadorX = p.jugadorX,
                    jugadorO = p.jugadorO,
                    ganador = p.ganador ?: "Empate",
                    fecha = Date().time, // ✅ Guardamos como Long
                    esFinalizada = true
                )
            )
        }
    }

    fun reiniciar() {
        partida?.let {
            partida = Partida(it.jugadorX, it.jugadorO)
        }
    }

    // ✅ Nuevas funciones para el historial
    fun obtenerPartidasPorJugador(nombreJugador: String): Flow<List<PartidaEntity>> {
        return repository.obtenerPartidasPorJugador(nombreJugador)
    }

    fun obtenerTodasLasPartidas(): Flow<List<PartidaEntity>> {
        return repository.obtenerTodasLasPartidas()
    }
}
