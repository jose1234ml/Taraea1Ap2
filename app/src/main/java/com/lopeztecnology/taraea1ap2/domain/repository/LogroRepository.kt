package com.lopeztecnology.taraea1ap2.data.repository

import com.lopeztecnology.taraea1ap2.data.local.LogroEntity
import com.lopeztecnology.taraea1ap2.data.local.LogroDao
import kotlinx.coroutines.flow.Flow

class LogroRepository(private val dao: LogroDao) {
    suspend fun insertarLogro(logro: LogroEntity) {
        dao.insertLogro(logro)
    }

    fun obtenerLogrosPorJugador(jugadorId: Int): Flow<List<LogroEntity>> =
        dao.obtenerLogrosPorJugador(jugadorId)

    fun obtenerTodosLosLogros(): Flow<List<LogroEntity>> =
        dao.obtenerTodosLosLogros()
}
