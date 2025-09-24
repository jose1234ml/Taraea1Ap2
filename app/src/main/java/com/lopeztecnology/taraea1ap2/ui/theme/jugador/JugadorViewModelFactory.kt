package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lopeztecnology.taraea1ap2.data.local.JugadorDatabase
import com.lopeztecnology.taraea1ap2.data.local.PartidaDatabase
import com.lopeztecnology.taraea1ap2.data.repository.JugadorRepositoryImpl
import com.lopeztecnology.taraea1ap2.data.repository.LogroRepository
import com.lopeztecnology.taraea1ap2.domain.usecase.GetJugadoresUseCase
import com.lopeztecnology.taraea1ap2.domain.usecase.InsertJugadorUseCase

class JugadorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = PartidaDatabase.getInstance(context)
        val logroRepo = LogroRepository(db.logroDao())
        val jugadorRepo = JugadorRepositoryImpl(JugadorDatabase.getInstance(context).jugadorDao())
        val insertUC = InsertJugadorUseCase(jugadorRepo)
        val getUC = GetJugadoresUseCase(jugadorRepo)

        @Suppress("UNCHECKED_CAST")
        return JugadorViewModel(insertUC, getUC, logroRepo) as T
    }
}

