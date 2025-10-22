package com.lopeztecnology.taraea1ap2.tarea.mapper

import com.lopeztecnology.taraea1ap2.data.dto.MovimientosDto
import com.lopeztecnology.taraea1ap2.domain.model.Movimientos

fun Movimientos.toDto(partidaId: Int): MovimientosDto = MovimientosDto(
    movimientoId = movimientoId,
    partidaId = partidaId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna,
)
fun MovimientosDto.toMovimientos(): Movimientos = Movimientos(
    movimientoId = movimientoId,
    partidaId = partidaId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna,
)

