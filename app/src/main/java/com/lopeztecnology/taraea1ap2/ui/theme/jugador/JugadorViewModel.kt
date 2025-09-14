package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lopeztecnology.taraea1ap2.data.local.Jugador
import com.lopeztecnology.taraea1ap2.domain.usecase.GetJugadoresUseCase
import com.lopeztecnology.taraea1ap2.domain.usecase.InsertJugadorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JugadorViewModel(
    private val insertJugador: InsertJugadorUseCase,
    private val getJugadores: GetJugadoresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JugadorState())
    val state: StateFlow<JugadorState> = _state

    init {
        cargarJugadores()
    }

    fun onEvent(event: JugadorEvent) {
        when (event) {
            is JugadorEvent.NombreChanged -> _state.value = _state.value.copy(nombre = event.value)
            is JugadorEvent.PartidasChanged -> _state.value = _state.value.copy(partidas = event.value)
            JugadorEvent.GuardarJugador -> guardar()
            JugadorEvent.ClearMessages -> _state.value = _state.value.copy(error = null, successMessage = null)
        }
    }

    private fun cargarJugadores() {
        viewModelScope.launch {
            val lista = getJugadores()
            _state.value = _state.value.copy(jugadores = lista)
        }
    }


    private fun guardar() {
        viewModelScope.launch {
            // validaciones
            val nombre = _state.value.nombre.trim()
            val partidas = _state.value.partidas.toIntOrNull() ?: -1

            if (nombre.isBlank() || partidas < 0) {
                _state.value = _state.value.copy(error = "Todos los campos son obligatorios")
                return@launch
            }

            val exito = insertJugador(Jugador(nombres = nombre, partidas = partidas))
            if (!exito) {
                _state.value = _state.value.copy(error = "Ya existe un jugador con ese nombre")
            } else {
                // recarga lista y limpia campos
                val lista = getJugadores()
                _state.value = _state.value.copy(
                    jugadores = lista,
                    nombre = "",
                    partidas = "",
                    error = null,
                    successMessage = "Jugador guardado correctamente"
                )
            }
        }
    }
}