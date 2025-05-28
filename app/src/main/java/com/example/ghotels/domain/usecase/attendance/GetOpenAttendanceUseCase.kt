package com.example.ghotels.domain.usecase.attendance

import com.example.ghotels.data.repository.AttendanceRepository
import com.example.ghotels.data.source.remote.AttendanceServiceClient
import com.example.ghotels.domain.model.Attendance

class GetOpenAttendanceUseCase(
    private val repository: AttendanceRepository
) {
    suspend operator fun invoke(employeeId: Long): Result<Attendance> {
        return repository.getOpenAttendance(employeeId)
            .map { it.toAttendance() } // Aquí haces la conversión
    }
}


