package com.lopeztecnology.taraea1ap2.data.remote

import com.lopeztecnology.taraea1ap2.data.remote.dto.JugadorDto
import com.lopeztecnology.taraea1ap2.domain.model.Jugador
import retrofit2.http.*

interface JugadoresApiService {

    @GET("api/Jugadores")
    suspend fun getJugadores(): List<JugadorDto>

    @POST("api/Jugadores")
    suspend fun postJugador(@Body jugador: JugadorDto): retrofit2.Response<JugadorDto>
}
