package com.example.ghotels.data.model

data class UserDto(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val dni: String,
    val socialSecurityNumber: String,
    val birthDate: String,
    val nationality: String? = null,
    val gender: String? = null,
    val maritalStatus: String? = null,
    val numberOfChildren: Int? = null,
    val disability: Boolean? = null,
    val contractType: String,
    val dailyWorkingHours: Int,
    val entryDate: String,
    val role: String?,
    val department: String?,
    val supervisorName: String?,
    val supervisorRole: String?,
    val address: AddressDto?,
    val emergencyContact: EmergencyContactDto?,
    val attendances: List<AttendanceDto> = emptyList(),
    val permissions: List<PermissionRequestDto> = emptyList()
)
