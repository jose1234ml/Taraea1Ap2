package com.lopeztecnology.taraea1ap2.tarea.mapper

import com.lopeztecnology.taraea1ap2.data.dto.MovimientosDto
import com.lopeztecnology.taraea1ap2.domain.model.Movimientos


fun MovimientosDto.asDomain(): Movimientos = Movimientos(
    movimientoId = this.movimientoId,
    partidaId = this.partidaId,
    jugador = this.jugador,
    posicionFila = this.posicionFila,
    posicionColumna = this.posicionColumna
)


fun Movimientos.asDto(forPartidaId: Int): MovimientosDto = MovimientosDto(
    movimientoId = this.movimientoId,
    partidaId = forPartidaId,
    jugador = this.jugador,
    posicionFila = this.posicionFila,
    posicionColumna = this.posicionColumna
)
