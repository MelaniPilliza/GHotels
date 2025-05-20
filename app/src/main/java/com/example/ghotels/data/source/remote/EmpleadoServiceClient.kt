package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.LoginRespuestaDto
import com.example.ghotels.data.model.RegistroEmpleadoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EmpleadoServiceClient {
    @POST("/api/registro")
    suspend fun registrar(@Body dto: RegistroEmpleadoDto): Response<Unit>

    @PUT("/api/registro/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body dto: RegistroEmpleadoDto): Response<Unit>

    @POST("/api/registro/login")
    suspend fun login(@Body loginDto: LoginDto): Response<LoginRespuestaDto>
}
