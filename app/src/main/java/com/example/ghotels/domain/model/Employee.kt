package com.example.ghotels.domain.model

data class Employee(
    val id: Long? = null,
    val dni: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val socialSecurityNumber: String,
    val birthDate: String, // ISO 8601

    val address: Address? = null,
    val nationality: String? = null,
    val gender: String? = null,
    val maritalStatus: String? = null,
    val numberOfChildren: Int = 0,
    val disability: Boolean = false,

    val contractType: String,
    val dailyWorkingHours: Int,
    val entryDate: String,

    val supervisorName: String? = null,
    val supervisorRole: String? = null,

    val role: String,
    val department: String,

    val emergencyContact: EmergencyContact? = null,
    val attendances: List<Attendance> = emptyList(),
    val permissions: List<PermissionRequest> = emptyList()
)

