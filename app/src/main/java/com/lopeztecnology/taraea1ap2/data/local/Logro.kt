package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logros")
data class Logro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreJugador: String,
    val descripcion: String,
    val fecha: Long = System.currentTimeMillis()
)
