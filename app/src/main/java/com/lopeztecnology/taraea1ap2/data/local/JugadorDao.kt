package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JugadorDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(jugador: Jugador): Long

    @Query("SELECT * FROM Jugadores ORDER BY jugadorId ASC")
    suspend fun getAll(): List<Jugador>

    @Query("SELECT * FROM Jugadores WHERE nombres = :nombre LIMIT 1")
    suspend fun findByName(nombre: String): Jugador?
}