package com.lopeztecnology.taraea1ap2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PartidaEntity::class], version = 1, exportSchema = false)
abstract class PartidaDatabase : RoomDatabase() {

    abstract fun partidaDao(): PartidaDao

    companion object {
        @Volatile
        private var INSTANCE: PartidaDatabase? = null

        fun getInstance(context: Context): PartidaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PartidaDatabase::class.java,
                    "partidas_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}