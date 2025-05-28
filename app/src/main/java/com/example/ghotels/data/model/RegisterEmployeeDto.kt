package com.example.ghotels.data.model

import com.example.ghotels.domain.model.Employee

data class RegisterEmployeeDto(
    val id: Long? = null,
    val dni: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
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
) {
    companion object {
        fun fromEmployee(employee: Employee): RegisterEmployeeDto {
            return RegisterEmployeeDto(
                id = employee.id,
                dni = employee.dni,
                firstName = employee.firstName,
                lastName = employee.lastName,
                email = employee.email,
                password = employee.password,
                phone = employee.phone,
                socialSecurityNumber = employee.socialSecurityNumber,
                birthDate = employee.birthDate,
                entryDate = employee.entryDate,
                contractType = employee.contractType,
                dailyWorkingHours = employee.dailyWorkingHours,
                roleId = employee.roleId,
                departmentId = employee.departmentId,
                supervisorId = employee.supervisorId,
                address = employee.address?.let {
                    AddressDto(
                        street = it.street,
                        postalCode = it.postalCode,
                        city = it.city,
                        province = it.province,
                        country = it.country
                    )
                },
                nationality = employee.nationality,
                gender = employee.gender,
                maritalStatus = employee.maritalStatus,
                numberOfChildren = employee.numberOfChildren,
                disability = employee.disability
            )
        }
    }
}