package com.lopeztecnology.taraea1ap2.tarea.local.Jugador

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lopeztecnology.taraea1ap2.domain.model.Jugador

@Entity(tableName = "Jugadores")
data class JugadorEntity(
    @PrimaryKey(autoGenerate = true)
    val jugadorId: Int = 0,
    val nombres: String,
    val partidas: Int,
    val needsSync: Boolean = false
)


fun JugadorEntity.toDomain() = Jugador(jugadorId, nombres, partidas)
fun Jugador.toEntity(needsSync: Boolean = false) =
    JugadorEntity(jugadorId, nombres, partidas, needsSync)
