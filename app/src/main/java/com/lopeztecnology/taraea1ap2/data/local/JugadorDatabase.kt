package com.lopeztecnology.taraea1ap2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Jugador::class, LogroEntity::class, PartidaEntity::class],
    version = 2,
    exportSchema = false
)
abstract class JugadorDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
    abstract fun logroDao(): LogroDao
    abstract fun partidaDao(): PartidaDao

    companion object {
        @Volatile
        private var INSTANCE: JugadorDatabase? = null

        fun getInstance(context: Context): JugadorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JugadorDatabase::class.java,
                    "jugadores_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
