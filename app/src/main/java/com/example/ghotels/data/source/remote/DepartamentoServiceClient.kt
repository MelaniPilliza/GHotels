package com.example.ghotels.data.source.remote

import com.example.ghotels.domain.model.Departamento
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DepartamentoServiceClient {
    @POST("/api/departamentos")
    suspend fun crear(@Body departamento: Departamento): Response<Departamento>

    @GET("/api/departamentos")
    suspend fun listarTodos(): List<Departamento>

    @GET("/api/departamentos/{id}")
    suspend fun obtenerPorId(@Path("id") id: Long): Response<Departamento>

    @GET("/api/departamentos/by-name")
    suspend fun obtenerPorNombre(@Query("nombre") nombre: String): Response<Departamento>

    @PUT("/api/departamentos/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body departamento: Departamento): Response<Departamento>

    @DELETE("/api/departamentos/{id}")
    suspend fun eliminar(@Path("id") id: Long): Response<Unit>
}
