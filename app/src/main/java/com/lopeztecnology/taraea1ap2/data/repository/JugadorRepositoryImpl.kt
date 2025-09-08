package com.lopeztecnology.taraea1ap2.data.repository


import com.lopeztecnology.taraea1ap2.data.local.Jugador
import com.lopeztecnology.taraea1ap2.data.local.JugadorDao
import com.lopeztecnology.taraea1ap2.domain.repository.JugadorRepository

class JugadorRepositoryImpl(
    private val dao: JugadorDao
) : JugadorRepository {
    override suspend fun insertJugador(jugador: Jugador): Boolean {
        // validar duplicado
        val existente = dao.findByName(jugador.nombres.trim())
        if (existente != null) return false
        dao.insert(jugador)
        return true
    }

    override suspend fun getJugadores(): List<Jugador> {
        return dao.getAll()
    }
}