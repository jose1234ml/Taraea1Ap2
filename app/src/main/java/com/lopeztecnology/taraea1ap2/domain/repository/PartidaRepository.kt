package com.lopeztecnology.taraea1ap2.data.repository

import com.lopeztecnology.taraea1ap2.data.local.PartidaDao
import com.lopeztecnology.taraea1ap2.data.local.PartidaEntity
import kotlinx.coroutines.flow.Flow

class PartidaRepository(private val dao: PartidaDao) {

    suspend fun guardarPartida(partida: PartidaEntity): Long {
        return dao.guardarPartida(partida)
    }

    suspend fun actualizarPartida(partida: PartidaEntity) {
        dao.actualizarPartida(partida)
    }

    fun obtenerPartidasPorJugador(nombreJugador: String): Flow<List<PartidaEntity>> {
        return dao.obtenerPartidasPorJugador(nombreJugador)
    }

    fun obtenerTodasLasPartidas(): Flow<List<PartidaEntity>> {
        return dao.obtenerTodasLasPartidas()
    }


}
