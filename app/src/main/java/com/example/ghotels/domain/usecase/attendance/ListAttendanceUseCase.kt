package com.example.ghotels.domain.usecase.attendance

import com.example.ghotels.data.repository.AttendanceRepository
import com.example.ghotels.domain.model.Attendance

class ListAttendanceUseCase(
    private val repository: AttendanceRepository
) {
    suspend operator fun invoke(employeeId: Long): Result<List<Attendance>> = try {
        val result = repository.getByEmployeeId(employeeId)
        Result.success(result.map { it.toAttendance() })
    } catch (e: Exception) {
        Result.failure(e)
    }
}

