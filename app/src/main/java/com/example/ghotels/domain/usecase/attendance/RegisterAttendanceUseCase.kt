package com.example.ghotels.domain.usecase.attendance

import com.example.ghotels.data.model.AttendanceDto
import com.example.ghotels.data.repository.AttendanceRepository
import com.example.ghotels.domain.model.Attendance

class RegisterAttendanceUseCase(
    private val repository: AttendanceRepository
) {
    suspend operator fun invoke(attendance: Attendance): Result<Unit> = try {
        val dto = AttendanceDto.from(attendance)
        val response = repository.register(dto)
        if (response.isSuccessful) Result.success(Unit)
        else Result.failure(Exception("Error al registrar asistencia"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
