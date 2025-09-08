package com.lopeztecnology.taraea1ap2.ui.theme.jugador


import com.lopeztecnology.taraea1ap2.data.local.Jugador

data class JugadorState(
    val jugadores: List<Jugador> = emptyList(),
    val nombre: String = "",
    val partidas: String = "",
    val error: String? = null,
    val successMessage:String?=null
)