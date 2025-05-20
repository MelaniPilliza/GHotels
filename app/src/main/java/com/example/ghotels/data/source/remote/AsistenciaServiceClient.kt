package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.AsistenciaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AsistenciaServiceClient {
    @POST("/api/asistencias")
    suspend fun crear(@Body dto: AsistenciaDto): Response<AsistenciaDto>

    @GET("/api/asistencias/empleado/{empleadoId}")
    suspend fun getPorEmpleado(@Path("empleadoId") empleadoId: Long): List<AsistenciaDto>
}
