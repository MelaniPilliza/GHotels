package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.PermissionTypeDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PermissionTypeServiceClient {
    @POST("/api/tipo-permisos")
    suspend fun add(@Body dto: PermissionTypeDto): Response<PermissionTypeDto>

    @GET("/api/tipo-permisos")
    suspend fun getAll(): List<PermissionTypeDto>

    @PUT("/api/tipo-permisos/{id}")
    suspend fun update(@Path("id") id: Long, @Body dto: PermissionTypeDto): Response<PermissionTypeDto>

    @DELETE("/api/tipo-permisos/{id}")
    suspend fun delete(@Path("id") id: Long): Response<Unit>
}
