package com.lopeztecnology.taraea1ap2.domain.usecase

import com.lopeztecnology.taraea1ap2.data.local.Jugador
import com.lopeztecnology.taraea1ap2.domain.repository.JugadorRepository

class InsertJugadorUseCase(private val repository: JugadorRepository) {
    suspend operator fun invoke(jugador: Jugador): Boolean {

        if (jugador.nombres.isBlank() || jugador.partidas < 0) return false
        return repository.insertJugador(jugador)
    }
}