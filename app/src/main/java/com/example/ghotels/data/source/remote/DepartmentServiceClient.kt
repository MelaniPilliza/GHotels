package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.DepartmentDto
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface DepartmentServiceClient {

    @GET("/api/departamentos")
    suspend fun getAll(): List<DepartmentDto>

    @POST("/api/departamentos")
    suspend fun add(@Body dto: DepartmentDto): Response<Void>

    @PUT("/api/departamentos/{id}")
    suspend fun update(
        @Path("id") id: Long,
        @Body dto: DepartmentDto
    ): Response<Void>

    @DELETE("/api/departamentos/{id}")
    suspend fun delete(@Path("id") id: Long): Response<Void>
}
