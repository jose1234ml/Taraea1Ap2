package com.lopeztecnology.taraea1ap2.tarea.local.Jugador

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao {

    @Query("SELECT * FROM Jugadores ORDER BY jugadorId DESC")
    fun observeAll(): Flow<List<JugadorEntity>>

    @Query("SELECT * FROM Jugadores WHERE jugadorId = :id")
    suspend fun getById(id: Int): JugadorEntity?

    @Upsert
    suspend fun upsert(jugador: JugadorEntity): Long

    @Query("SELECT * FROM Jugadores WHERE nombres COLLATE NOCASE = :name COLLATE NOCASE LIMIT 1")
    suspend fun getJugadorByName(name: String): JugadorEntity?

    @Delete
    suspend fun delete(jugador: JugadorEntity)

    @Query("DELETE FROM Jugadores WHERE jugadorId = :id")
    suspend fun delete(id: Int)


    @Query("SELECT * FROM Jugadores WHERE needsSync = 1")
    suspend fun getPendientesSync(): List<JugadorEntity>

    @Query("UPDATE Jugadores SET needsSync = 0 WHERE jugadorId = :id")
    suspend fun marcarSincronizado(id: Int)
}
