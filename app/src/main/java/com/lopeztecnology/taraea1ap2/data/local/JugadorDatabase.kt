package com.lopeztecnology.taraea1ap2.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Jugador::class], version = 1, exportSchema = false)
abstract class JugadorDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao

    companion object {
        @Volatile
        private var INSTANCE: JugadorDatabase? = null

        fun getInstance(context: Context): JugadorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JugadorDatabase::class.java,
                    "jugadores_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}