package com.example.ghotels.data.repository

import com.example.ghotels.data.model.AttendanceDto
import com.example.ghotels.data.source.remote.AttendanceServiceClient
import com.example.ghotels.domain.model.Attendance
import retrofit2.Response

class AttendanceRepository(
    private val serviceClient: AttendanceServiceClient
) {
    suspend fun getByEmployeeId(employeeId: Long): List<AttendanceDto> {
        return serviceClient.obtenerAsistencias(employeeId)
    }

    suspend fun register(attendanceDto: AttendanceDto): Response<Unit> {
        return serviceClient.add(attendanceDto)
    }

    suspend fun getOpenAttendance(employeeId: Long): Result<AttendanceDto> = runCatching {
        val response = serviceClient.getOpenAttendance(employeeId)
        response.body() ?: throw Exception("No se encontró asistencia abierta")
    }

}
