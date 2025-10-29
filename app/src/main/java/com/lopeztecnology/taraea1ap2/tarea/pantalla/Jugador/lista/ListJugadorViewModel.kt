package com.lopeztecnology.taraea1ap2.tarea.pantalla.Jugador.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lopeztecnology.taraea1ap2.data.remote.JugadoresApiService
import com.lopeztecnology.taraea1ap2.domain.repository.JugadorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListJugadorViewModel @Inject constructor(
    private val repository: JugadorRepository,
    private val apiService: JugadoresApiService
) : ViewModel() {

    val state: StateFlow<ListJugadorUiState> =
        repository.observeJugador()
            .map { jugadores ->
                ListJugadorUiState(
                    jugadores = jugadores,
                    isLoading = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ListJugadorUiState(isLoading = true),
            )

    init {

        viewModelScope.launch {
            state.collectLatest { uiState ->
                try {
                    repository.syncJugadores(apiService)
                } catch (e: Exception) {
                    println("Error al sincronizar jugadores: ${e.message}")
                }
            }
        }
    }

    fun onEvent(event: ListJugadorUiEvent) {
        when (event) {
            is ListJugadorUiEvent.OnDeleteJugadorClick -> {
                viewModelScope.launch {
                    try {
                        repository.deleteJugador(event.jugador.jugadorId)
                        event.onSuccess()
                    } catch (e: Exception) {
                        println("Error al eliminar jugador: ${e.message}")
                    }
                }
            }
        }
    }
}
