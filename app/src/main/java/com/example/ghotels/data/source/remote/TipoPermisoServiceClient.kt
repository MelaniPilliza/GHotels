package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.TipoPermisoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TipoPermisoServiceClient {
    @POST("/api/tipo-permisos")
    suspend fun crear(@Body dto: TipoPermisoDto): Response<TipoPermisoDto>

    @GET("/api/tipo-permisos")
    suspend fun listarTodos(): List<TipoPermisoDto>

    @GET("/api/tipo-permisos/{id}")
    suspend fun obtenerPorId(@Path("id") id: Long): Response<TipoPermisoDto>

    @PUT("/api/tipo-permisos/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body dto: TipoPermisoDto): Response<TipoPermisoDto>

    @DELETE("/api/tipo-permisos/{id}")
    suspend fun eliminar(@Path("id") id: Long): Response<Unit>
}
