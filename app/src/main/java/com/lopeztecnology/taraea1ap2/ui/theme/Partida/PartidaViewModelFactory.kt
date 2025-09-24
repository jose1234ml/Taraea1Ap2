package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lopeztecnology.taraea1ap2.data.local.PartidaDatabase
import com.lopeztecnology.taraea1ap2.data.repository.PartidaRepository

class PartidaViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = PartidaDatabase.getInstance(context)
        val repo = PartidaRepository(db.partidaDao())
        @Suppress("UNCHECKED_CAST")
        return PartidaViewModel(repo) as T
    }
}
