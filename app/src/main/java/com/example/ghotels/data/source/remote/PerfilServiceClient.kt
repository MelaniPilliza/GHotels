package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.ContactoEmergenciaDto
import com.example.ghotels.data.model.PerfilDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface PerfilServiceClient {
    @PUT("/api/perfil/{empleadoId}/datos")
    suspend fun actualizarPerfil(
        @Path("empleadoId") empleadoId: Long,
        @Body dto: PerfilDto
    ): Response<Unit>

    @PUT("/api/perfil/{empleadoId}/contacto-emergencia")
    suspend fun actualizarContacto(
        @Path("empleadoId") empleadoId: Long,
        @Body contactoDto: ContactoEmergenciaDto
    ): Response<Unit>
}
