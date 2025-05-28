package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.OfficialHolidayDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OfficialHolidayServiceClient {
    @GET("/api/festivos")
    suspend fun getAll(): List<OfficialHolidayDto>

    @POST("/api/festivos")
    suspend fun add(@Body dto: OfficialHolidayDto): Response<Void>

    @PUT("/api/festivos/{id}")
    suspend fun update(
        @Path("id") id: Long,
        @Body dto: OfficialHolidayDto
    ): Response<Void>

    @DELETE("/api/festivos/{id}")
    suspend fun delete(@Path("id") id: Long): Response<Void>
}
