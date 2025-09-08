package com.lopeztecnology.taraea1ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.lopeztecnology.taraea1ap2.ui.theme.jugador.JugadorNavHost
import com.lopeztecnology.taraea1ap2.ui.theme.jugador.JugadorViewModel
import com.lopeztecnology.taraea1ap2.ui.theme.jugador.JugadorViewModelFactory
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {

    private val viewModel: JugadorViewModel by viewModels {
        JugadorViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Usamos NavHost para manejar la navegación entre crear y listado
                JugadorNavHost(viewModel = viewModel)
            }
        }
    }
}
