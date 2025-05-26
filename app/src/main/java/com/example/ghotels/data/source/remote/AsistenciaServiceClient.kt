package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.AsistenciaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AsistenciaServiceClient {
    @GET("/api/asistencias/empleado/{id}")
    suspend fun obtenerAsistencias(@Path("id") empleadoId: Long): List<AsistenciaDto>

    @POST("/api/asistencias")
    suspend fun registrarAsistencia(@Body asistencia: AsistenciaDto): Response<Unit>
}
