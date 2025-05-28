package com.example.ghotels.domain.model

// Asistencia diaria del empleado
data class Attendance(
    val id: Long? = null,
    val entryTime: String,
    val exitTime: String?,
    val reasonForNonCompliance: String? = null,
    val employeeId: Long
)
