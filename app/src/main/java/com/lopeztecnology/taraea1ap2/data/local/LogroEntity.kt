package com.lopeztecnology.taraea1ap2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logros")
data class LogroEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jugadorId: Int,
    val descripcion: String,
    val fecha: Long = System.currentTimeMillis()
)
