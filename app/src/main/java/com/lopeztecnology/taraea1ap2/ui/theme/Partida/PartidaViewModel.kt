package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lopeztecnology.taraea1ap2.data.local.Jugador
import com.lopeztecnology.taraea1ap2.data.local.LogroEntity
import com.lopeztecnology.taraea1ap2.data.local.PartidaEntity
import com.lopeztecnology.taraea1ap2.data.repository.PartidaRepository
import com.lopeztecnology.taraea1ap2.data.repository.LogroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

data class JugadorLogros(
    val jugador: Jugador,
    val cantidadLogros: Int,
    val logros: List<String> = listOf()
)

class PartidaViewModel(
    private val repository: PartidaRepository,
    private val logroRepository: LogroRepository
) : ViewModel() {

    var partida by mutableStateOf<Partida?>(null)
        private set

    private val jsonFormat = Json { encodeDefaults = true; ignoreUnknownKeys = true }
    private var partidaActualEntityId: Int? = null

    private val _jugadorSeleccionado = mutableStateOf<JugadorLogros?>(null)
    val jugadorSeleccionado: State<JugadorLogros?> = _jugadorSeleccionado


    private val _notificacionLogro = MutableStateFlow<String?>(null)
    val notificacionLogro: StateFlow<String?> = _notificacionLogro


    private var jugadorXId: Int? = null
    private var jugadorOId: Int? = null

    fun seleccionarJugadorLogros(jugadorLogros: JugadorLogros) {
        _jugadorSeleccionado.value = jugadorLogros
    }

    fun iniciarPartida(jugadorX: Jugador, jugadorO: Jugador) {
        jugadorXId = jugadorX.jugadorId
        jugadorOId = jugadorO.jugadorId

        val nuevaPartida = Partida(jugadorX = jugadorX.nombres, jugadorO = jugadorO.nombres)
        partida = nuevaPartida
        guardarPartida(nuevaPartida, esNueva = true)
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
        val xId = jugadorXId ?: return
        val oId = jugadorOId ?: return

        if (p.tablero[fila][col].isEmpty() && p.ganador == null) {
            p.tablero[fila][col] = p.turno

            when {
                verificarGanador(p.tablero, p.turno) -> {
                    p.ganador = p.turno
                    generarLogros(p, xId, oId)
                }
                tableroLleno(p.tablero) -> {
                    p.ganador = "Empate"
                    generarLogros(p, xId, oId)
                }
                else -> p.turno = if (p.turno == "X") "O" else "X"
            }

            partida = p.copy(tablero = Array(3) { p.tablero[it].copyOf() })
            guardarPartida(p)
        }
    }

    private fun generarLogros(partida: Partida, jugadorXId: Int, jugadorOId: Int) {
        viewModelScope.launch {
            val logros = mutableListOf<LogroEntity>()


            val jugadorXLogros = logroRepository.obtenerLogrosPorJugadorSuspend(jugadorXId)
            val jugadorOLogros = logroRepository.obtenerLogrosPorJugadorSuspend(jugadorOId)


            when (partida.ganador) {
                "X" -> {
                    logros.add(LogroEntity(jugadorId = jugadorXId, descripcion = "¡Ganó la partida!"))
                    _notificacionLogro.value = "Jugador ${partida.jugadorX}: ¡Ganó la partida!"
                    if (jugadorXLogros.none { it.descripcion.contains("¡Ganó la partida!") }) {
                        logros.add(LogroEntity(jugadorId = jugadorXId, descripcion = "Primera victoria"))
                        _notificacionLogro.value = "Jugador ${partida.jugadorX}: Primera victoria"
                    }
                }
                "O" -> {
                    logros.add(LogroEntity(jugadorId = jugadorOId, descripcion = "¡Ganó la partida!"))
                    _notificacionLogro.value = "Jugador ${partida.jugadorO}: ¡Ganó la partida!"
                    if (jugadorOLogros.none { it.descripcion.contains("¡Ganó la partida!") }) {
                        logros.add(LogroEntity(jugadorId = jugadorOId, descripcion = "Primera victoria"))
                        _notificacionLogro.value = "Jugador ${partida.jugadorO}: Primera victoria"
                    }
                }
                "Empate" -> {
                    logros.add(LogroEntity(jugadorId = jugadorXId, descripcion = "Empate épico"))
                    logros.add(LogroEntity(jugadorId = jugadorOId, descripcion = "Empate épico"))
                    _notificacionLogro.value = "Empate épico entre ${partida.jugadorX} y ${partida.jugadorO}"
                }
            }


            if (partida.turno == "X" && jugadorXLogros.size >= 5) {
                logros.add(LogroEntity(jugadorId = jugadorXId, descripcion = "5 partidas jugadas"))
                _notificacionLogro.value = "Jugador ${partida.jugadorX}: 5 partidas jugadas"
            }
            if (partida.turno == "O" && jugadorOLogros.size >= 5) {
                logros.add(LogroEntity(jugadorId = jugadorOId, descripcion = "5 partidas jugadas"))
                _notificacionLogro.value = "Jugador ${partida.jugadorO}: 5 partidas jugadas"
            }

            logros.forEach { logroRepository.insertarLogro(it) }
        }
    }

    fun reiniciar() {
        partida?.let {
            val nuevaPartida = Partida(it.jugadorX, it.jugadorO)
            partida = nuevaPartida
            guardarPartida(nuevaPartida, esNueva = true)
        }
    }


    fun obtenerTodasLasPartidas(): Flow<List<PartidaEntity>> {
        return repository.obtenerTodasLasPartidas()
    }

    fun clearNotificacion() {
        _notificacionLogro.value = null
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
