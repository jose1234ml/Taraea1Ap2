package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidas")
data class PartidaEntity(
    @PrimaryKey(autoGenerate = true) val partidaId: Int = 0,
    val jugadorX: String,
    val jugadorO: String,
    val turno: String,
    val tablero: String,
    val ganador: String?,
    val fecha: Long,
    val esFinalizada: Boolean
)
