package com.lopeztecnology.taraea1ap2.tarea.mapper

import com.lopeztecnology.taraea1ap2.domain.model.Jugador
import com.lopeztecnology.taraea1ap2.tarea.local.Jugador.JugadorEntity
import com.lopeztecnology.taraea1ap2.data.remote.dto.JugadorDto


fun JugadorEntity.toDomain(): Jugador = Jugador(
    jugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas
)


fun Jugador.toEntity(needsSync: Boolean = false): JugadorEntity = JugadorEntity(
    jugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas,
    needsSync = needsSync
)

fun Jugador.toDto(): JugadorDto = JugadorDto(
    jugadorId = null,
    nombres = nombres,
    email = "${nombres.lowercase()}@ejemplo.com"
)


