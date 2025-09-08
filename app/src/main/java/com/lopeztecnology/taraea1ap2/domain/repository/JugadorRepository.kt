package com.lopeztecnology.taraea1ap2.domain.repository

import com.lopeztecnology.taraea1ap2.data.local.Jugador

interface JugadorRepository {
    suspend fun insertJugador(jugador: Jugador): Boolean
    suspend fun getJugadores(): List<Jugador>
}