package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(partida: PartidaEntity)

    @Query("SELECT * FROM partidas WHERE jugadorX = :nombreJugador OR jugadorO = :nombreJugador ORDER BY fecha DESC")
    fun getPartidasPorJugador(nombreJugador: String): Flow<List<PartidaEntity>>

    @Query("SELECT * FROM partidas ORDER BY fecha DESC")
    fun getTodasLasPartidas(): Flow<List<PartidaEntity>>
}
