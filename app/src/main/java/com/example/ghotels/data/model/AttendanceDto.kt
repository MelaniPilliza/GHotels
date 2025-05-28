package com.example.ghotels.data.model

import com.example.ghotels.domain.model.Attendance

data class AttendanceDto(
    val id: Long? = null,
    val entryTime: String,
    val exitTime: String? = null,
    val reasonForNonCompliance: String? = null,
    val employeeId: Long
) {
    fun toAttendance() = Attendance(
        id = id,
        entryTime = entryTime,
        exitTime = exitTime,
        reasonForNonCompliance = reasonForNonCompliance,
        employeeId = employeeId
    )

    companion object {
        fun from(attendance: Attendance) = AttendanceDto(
            id = attendance.id,
            entryTime = attendance.entryTime,
            exitTime = attendance.exitTime,
            reasonForNonCompliance = attendance.reasonForNonCompliance,
            employeeId = attendance.employeeId
        )
    }
}
