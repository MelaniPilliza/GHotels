package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.data.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface EmployeeServiceClient {
    @POST("/api/registro")
    suspend fun register(@Body dto: RegisterEmployeeDto): Response<Unit>

    @PUT("/api/registro/{id}")
    suspend fun update(@Path("id") id: Long, @Body dto: RegisterEmployeeDto): Response<Unit>

    @POST("/api/registro/login")
    suspend fun login(@Body loginDto: LoginDto): Response<UserDto>

    @GET("/api/registro/empleados")
    suspend fun getEmployees(): List<UserDto>

    @PUT("/api/registro/perfil/{id}")
    suspend fun updateProfile(@Path("id") id: Long, @Body dto: UserDto): Response<Unit>
}

