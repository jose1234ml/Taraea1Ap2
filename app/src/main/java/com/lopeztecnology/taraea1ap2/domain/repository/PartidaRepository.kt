package com.lopeztecnology.taraea1ap2.data.repository

import com.lopeztecnology.taraea1ap2.data.local.PartidaDao
import com.lopeztecnology.taraea1ap2.data.local.PartidaEntity
import kotlinx.coroutines.flow.Flow

class PartidaRepository(private val dao: PartidaDao) {

    suspend fun guardarPartida(partida: PartidaEntity) {
        dao.insert(partida)
    }

    fun obtenerPartidasPorJugador(nombreJugador: String): Flow<List<PartidaEntity>> {
        return dao.getPartidasPorJugador(nombreJugador)
    }

    fun obtenerTodasLasPartidas(): Flow<List<PartidaEntity>> {
        return dao.getTodasLasPartidas()
    }
}
