package com.lopeztecnology.taraea1ap2.domain.model 

data class Movimientos(
    val movimientoId: Int = 0,
    val partidaId: Int = 0,
    val jugador: String,
    val posicionFila: Int,
    val posicionColumna: Int,
)