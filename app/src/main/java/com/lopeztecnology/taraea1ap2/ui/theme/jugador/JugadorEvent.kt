package com.lopeztecnology.taraea1ap2.ui.theme.jugador


sealed class JugadorEvent {
    data class NombreChanged(val value: String) : JugadorEvent()
    data class PartidasChanged(val value: String) : JugadorEvent()
    object GuardarJugador : JugadorEvent()
    object ClearMessages : JugadorEvent()
}