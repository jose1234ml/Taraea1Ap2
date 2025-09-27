package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPartida(partida: PartidaEntity): Long

    @Update
    suspend fun actualizarPartida(partida: PartidaEntity)

    @Query("SELECT * FROM partidas WHERE jugadorX = :nombreJugador OR jugadorO = :nombreJugador ORDER BY fecha DESC LIMIT 1")
    suspend fun obtenerUltimaPartida(nombreJugador: String): PartidaEntity?

    @Query("SELECT * FROM partidas WHERE jugadorX = :nombreJugador OR jugadorO = :nombreJugador ORDER BY fecha DESC")
    fun obtenerPartidasPorJugador(nombreJugador: String): Flow<List<PartidaEntity>>

    @Query("SELECT * FROM partidas ORDER BY fecha DESC")
    fun obtenerTodasLasPartidas(): Flow<List<PartidaEntity>>
}
