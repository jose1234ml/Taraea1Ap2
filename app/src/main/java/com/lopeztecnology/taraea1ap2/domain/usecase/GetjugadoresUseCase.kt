package com.lopeztecnology.taraea1ap2.domain.usecase

import com.lopeztecnology.taraea1ap2.data.local.Jugador
import com.lopeztecnology.taraea1ap2.domain.repository.JugadorRepository

class GetJugadoresUseCase(private val repository: JugadorRepository) {
    suspend operator fun invoke(): List<Jugador> = repository.getJugadores()
}