package com.lopeztecnology.taraea1ap2.data.remote

import com.lopeztecnology.taraea1ap2.data.dto.MovimientosDto
import javax.inject.Inject

class MovimientosRemoteDataSource @Inject constructor(
    private val api: MovimientosApi
) {
    suspend fun getMovimientos(partidaId: Int) = api.getMovimientos(partidaId)
    suspend fun postMovimiento(partidaId: Int, movimiento: MovimientosDto) =
        api.postMovimiento(movimiento)
}