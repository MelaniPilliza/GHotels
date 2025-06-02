package com.example.ghotels.data.model

data class UpdateEmployeeDto(
    val id: Long? = null,
    val dni: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val socialSecurityNumber: String,
    val birthDate: String,
    val entryDate: String?,
    val contractType: String,
    val dailyWorkingHours: Int,
    val roleId: Long,
    val departmentId: Long,
    val supervisorId: Long? = null,
    val address: AddressDto? = null,
    val nationality: String? = null,
    val gender: String? = null,
    val maritalStatus: String? = null,
    val numberOfChildren: Int? = null,
    val disability: Boolean? = null
)