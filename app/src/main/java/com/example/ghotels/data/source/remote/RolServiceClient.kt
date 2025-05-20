package com.example.ghotels.data.source.remote

import com.example.ghotels.domain.model.Rol
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RolServiceClient {
    @POST("/api/roles")
    suspend fun crear(@Body rol: Rol): Response<Rol>

    @GET("/api/roles")
    suspend fun listarTodos(): List<Rol>

    @GET("/api/roles/{id}")
    suspend fun obtenerPorId(@Path("id") id: Long): Response<Rol>

    @GET("/api/roles/by-name")
    suspend fun obtenerPorNombre(@Query("nombre") nombre: String): Response<Rol>

    @PUT("/api/roles/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body rol: Rol): Response<Rol>

    @DELETE("/api/roles/{id}")
    suspend fun eliminar(@Path("id") id: Long): Response<Unit>
}
