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
import kotlinx.serialization.builtins.ArraySerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
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

    private val jsonFormat = Json { encodeDefaults = true; ignoreUnknownKeys = true }

    private var partidaActualEntityId: Int? = null



    fun iniciarPartida(jugadorX: Jugador, jugadorO: Jugador) {
        val nuevaPartida = Partida(jugadorX = jugadorX.nombres, jugadorO = jugadorO.nombres)
        partida = nuevaPartida
        guardarPartida(nuevaPartida, esNueva = true)
    }

    fun cargarUltimaPartida(nombreJugador: String) {
        viewModelScope.launch {
            try {
                val ultima = repository.obtenerUltimaPartida(nombreJugador)
                ultima?.let {
                    partidaActualEntityId = it.partidaId
                    cargarPartidaSeleccionada(it)
                }
            } catch (e: Exception) {
                partida = Partida(jugadorX = nombreJugador, jugadorO = "Oponente")
            }
        }
    }

    fun cargarPartidaSeleccionada(partidaEntity: PartidaEntity) {
        try {
            val tableroCargado = if (partidaEntity.tablero.isNotEmpty()) {
                jsonFormat.decodeFromString(
                    ArraySerializer(ArraySerializer(String.serializer())),
                    partidaEntity.tablero
                )
            } else {
                Array(3) { Array(3) { "" } }
            }
            partida = Partida(
                jugadorX = partidaEntity.jugadorX,
                jugadorO = partidaEntity.jugadorO,
                turno = partidaEntity.turno.ifEmpty { "X" },
                tablero = tableroCargado,
                ganador = partidaEntity.ganador
            )
            partidaActualEntityId = partidaEntity.partidaId
        } catch (e: Exception) {
            partida = Partida(
                jugadorX = partidaEntity.jugadorX,
                jugadorO = partidaEntity.jugadorO
            )
        }
    }

    fun jugar(fila: Int, col: Int) {
        val p = partida ?: return
        if (p.tablero[fila][col].isEmpty() && p.ganador == null) {
            p.tablero[fila][col] = p.turno

            if (verificarGanador(p.tablero, p.turno)) {
                p.ganador = p.turno
            } else if (tableroLleno(p.tablero)) {
                p.ganador = "Empate"
            } else {
                p.turno = if (p.turno == "X") "O" else "X"
            }


            partida = p.copy(tablero = Array(3) { p.tablero[it].copyOf() })


            guardarPartida(p)
        }
    }

    fun reiniciar() {
        partida?.let {
            val nuevaPartida = Partida(it.jugadorX, it.jugadorO)
            partida = nuevaPartida
            guardarPartida(nuevaPartida, esNueva = true)
        }
    }

    fun obtenerPartidasPorJugador(nombreJugador: String): Flow<List<PartidaEntity>> {
        return repository.obtenerPartidasPorJugador(nombreJugador)
    }

    fun obtenerTodasLasPartidas(): Flow<List<PartidaEntity>> {
        return repository.obtenerTodasLasPartidas()
    }

    private fun tableroLleno(tablero: Array<Array<String>>) = tablero.all { fila -> fila.all { it.isNotEmpty() } }

    private fun verificarGanador(tablero: Array<Array<String>>, jugador: String): Boolean {
        if (tablero.any { fila -> fila.all { it == jugador } }) return true
        for (i in 0..2) if ((0..2).all { tablero[it][i] == jugador }) return true
        if ((0..2).all { tablero[it][it] == jugador }) return true
        if ((0..2).all { tablero[it][2 - it] == jugador }) return true
        return false
    }

    private fun guardarPartida(p: Partida, esNueva: Boolean = false) {
        viewModelScope.launch {
            val partidaEntity = PartidaEntity(
                partidaId = if (esNueva) 0 else partidaActualEntityId ?: 0,
                jugadorX = p.jugadorX,
                jugadorO = p.jugadorO,
                turno = p.turno,
                tablero = jsonFormat.encodeToString(ArraySerializer(ArraySerializer(String.serializer())), p.tablero),
                ganador = p.ganador,
                fecha = Date().time,
                esFinalizada = p.ganador != null
            )

            try {
                if (esNueva) {
                    val idGenerado = repository.guardarPartida(partidaEntity)
                    partidaActualEntityId = idGenerado.toInt()
                } else {
                    repository.actualizarPartida(partidaEntity)
                }
            } catch (_: Exception) { }
        }
    }
}