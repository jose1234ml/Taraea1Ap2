package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidas")
data class PartidaEntity(
    @PrimaryKey(autoGenerate = true) val partidaId: Int = 0,
    val jugadorX: String,
    val jugadorO: String,
    val turno: String = "X",
    val tablero: String = "",
    val ganador: String? = null,
    val fecha: Long = System.currentTimeMillis(),
    val esFinalizada: Boolean = false
)