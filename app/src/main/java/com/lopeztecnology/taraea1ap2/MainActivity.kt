package com.lopeztecnology.taraea1ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.lopeztecnology.taraea1ap2.ui.theme.jugador.*

class MainActivity : ComponentActivity() {

    private val jugadorViewModel: JugadorViewModel by viewModels {
        JugadorViewModelFactory(applicationContext)
    }

    // Creamos el ViewModel de Partida usando la Factory que inyecta el repository
    private val partidaViewModel: PartidaViewModel by viewModels {
        PartidaViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // NavHost con los dos ViewModels
                JugadorNavHost(
                    viewModel = jugadorViewModel,
                    partidaViewModel = partidaViewModel
                )
            }
        }
    }
}
