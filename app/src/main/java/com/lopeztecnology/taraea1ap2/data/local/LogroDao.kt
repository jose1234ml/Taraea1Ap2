package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogroDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLogro(logro: LogroEntity)

    @Query("SELECT * FROM logros WHERE jugadorId = :jugadorId ORDER BY fecha DESC")
    fun obtenerLogrosPorJugador(jugadorId: Int): Flow<List<LogroEntity>>

    @Query("SELECT * FROM logros ORDER BY fecha DESC")
    fun obtenerTodosLosLogros(): Flow<List<LogroEntity>>
}
