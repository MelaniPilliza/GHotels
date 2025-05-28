package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.RoleDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoleServiceClient {
    @GET("api/roles")
    suspend fun getAll(): List<RoleDto>

    @POST("api/roles")
    suspend fun add(@Body dto: RoleDto): Response<Void>

    @PUT("api/roles/{id}")
    suspend fun update(@Path("id") id: Long, @Body dto: RoleDto): Response<Void>

    @DELETE("api/roles/{id}")
    suspend fun delete(@Path("id") id: Long): Response<Void>
}


