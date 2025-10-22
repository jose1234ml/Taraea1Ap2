package com.lopeztecnology.taraea1ap2.data.repository

import androidx.room.util.copy
import com.lopeztecnology.taraea1ap2.data.remote.MovimientosRemoteDataSource
import com.lopeztecnology.taraea1ap2.domain.model.Movimientos
import com.lopeztecnology.taraea1ap2.tarea.mapper.toDto
import com.lopeztecnology.taraea1ap2.tarea.mapper.toMovimientos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovimientosRepository @Inject constructor(
    private val remoteDataSource: MovimientosRemoteDataSource
) {
    suspend fun getMovimientos(partidaId: Int): Flow<List<Movimientos>> = flow {
        val response = remoteDataSource.getMovimientos(partidaId)
        val movimientos = response.map {
            it.toMovimientos().copy(partidaId = partidaId)
        }
        emit(movimientos)
    }.catch { e ->
        e.printStackTrace()
        emit(emptyList())
    }

    suspend fun postMovimiento(partidaId: Int, movimiento: Movimientos): Boolean {
        try {
            val response = remoteDataSource.postMovimiento(partidaId, movimiento.toDto(partidaId))
            return response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

}