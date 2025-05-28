package com.example.ghotels.data.source.remote

import com.example.ghotels.data.model.AttendanceDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AttendanceServiceClient {
    @GET("/api/asistencias/empleado/{id}")
    suspend fun obtenerAsistencias(@Path("id") empleadoId: Long): List<AttendanceDto>

    @POST("/api/asistencias")
    suspend fun add(@Body asistencia: AttendanceDto): Response<Unit>

    @GET("/api/asistencias/abierta/{empleadoId}")
    suspend fun getOpenAttendance(@Path("empleadoId") empleadoId: Long): Response<AttendanceDto>

}
