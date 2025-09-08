package com.lopeztecnology.taraea1ap2.ui.theme.jugador

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lopeztecnology.taraea1ap2.data.local.JugadorDatabase
import com.lopeztecnology.taraea1ap2.data.repository.JugadorRepositoryImpl
import com.lopeztecnology.taraea1ap2.domain.usecase.GetJugadoresUseCase
import com.lopeztecnology.taraea1ap2.domain.usecase.InsertJugadorUseCase

class JugadorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = JugadorDatabase.getInstance(context)
        val repo = JugadorRepositoryImpl(db.jugadorDao())
        val insertUC = InsertJugadorUseCase(repo)
        val getUC = GetJugadoresUseCase(repo)
        @Suppress("UNCHECKED_CAST")
        return JugadorViewModel(insertUC, getUC) as T
    }
}