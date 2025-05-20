package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.SolicitudPermisoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SolicitudPermisoServiceClient {
    @POST("/api/solicitudes")
    suspend fun crear(@Body dto: SolicitudPermisoDto): Response<SolicitudPermisoDto>

    @GET("/api/solicitudes")
    suspend fun listarTodas(): List<SolicitudPermisoDto>

    @GET("/api/solicitudes/empleado/{empleadoId}")
    suspend fun listarPorEmpleado(@Path("empleadoId") empleadoId: Long): List<SolicitudPermisoDto>

    @PUT("/api/solicitudes/aprobar/{id}")
    suspend fun aprobar(@Path("id") id: Long): Response<Unit>

    @PUT("/api/solicitudes/rechazar/{id}")
    suspend fun rechazar(@Path("id") id: Long): Response<Unit>
}
