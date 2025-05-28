package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.PermissionRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PermissionRequestServiceClient {
    @POST("/api/solicitudes")
    suspend fun add(@Body dto: PermissionRequestDto): Response<PermissionRequestDto>

    @GET("/api/solicitudes")
    suspend fun getall(): List<PermissionRequestDto>

    @GET("/api/solicitudes/empleado/{empleadoId}")
    suspend fun listEmployeeById(@Path("empleadoId") empleadoId: Long): List<PermissionRequestDto>

    @PUT("/api/solicitudes/aprobar/{id}")
    suspend fun approve(@Path("id") id: Long): Response<Unit>

    @PUT("/api/solicitudes/rechazar/{id}")
    suspend fun reject(@Path("id") id: Long): Response<Unit>
}
