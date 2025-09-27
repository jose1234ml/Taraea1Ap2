package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Jugadores")
data class Jugador(
    @PrimaryKey(autoGenerate = true) val jugadorId: Int = 0,
    val nombres: String,
    val partidas: Int
)
