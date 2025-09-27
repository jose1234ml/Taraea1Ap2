package com.lopeztecnology.taraea1ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.MaterialTheme
import com.lopeztecnology.taraea1ap2.data.local.JugadorDatabase
import com.lopeztecnology.taraea1ap2.data.local.PartidaDatabase
import com.lopeztecnology.taraea1ap2.data.repository.JugadorRepositoryImpl
import com.lopeztecnology.taraea1ap2.data.repository.PartidaRepository
import com.lopeztecnology.taraea1ap2.data.repository.LogroRepository
import com.lopeztecnology.taraea1ap2.domain.usecase.GetJugadoresUseCase
import com.lopeztecnology.taraea1ap2.domain.usecase.InsertJugadorUseCase
import com.lopeztecnology.taraea1ap2.ui.theme.jugador.JugadorNavHost
import com.lopeztecnology.taraea1ap2.ui.theme.jugador.JugadorViewModel
import com.lopeztecnology.taraea1ap2.ui.theme.jugador.PartidaViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val jugadorDb = JugadorDatabase.getInstance(applicationContext)
        val partidaDb = PartidaDatabase.getInstance(applicationContext)

        val logroRepo = LogroRepository(partidaDb.logroDao())
        val jugadorRepo = JugadorRepositoryImpl(jugadorDb.jugadorDao())
        val insertUC = InsertJugadorUseCase(jugadorRepo)
        val getUC = GetJugadoresUseCase(jugadorRepo)
        val partidaRepo = PartidaRepository(partidaDb.partidaDao())


        val jugadorFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return JugadorViewModel(insertUC, getUC, logroRepo) as T
            }
        }

        val partidaFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PartidaViewModel(partidaRepo, logroRepo) as T
            }
        }

        setContent {
            MaterialTheme {

                val jugadorVM: JugadorViewModel = viewModel(factory = jugadorFactory)
                val partidaVM: PartidaViewModel = viewModel(factory = partidaFactory)

                JugadorNavHost(
                    viewModel = jugadorVM,
                    partidaViewModel = partidaVM
                )
            }
        }
    }
}
