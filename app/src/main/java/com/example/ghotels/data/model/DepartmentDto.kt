package com.example.ghotels.data.model

import com.example.ghotels.domain.model.Department

data class DepartmentDto(
    val id: Long? = null,
    val name: String
) {
    fun toDepartment(): Department = Department(
        id = id,
        name = name
    )

    companion object {
        fun from(department: Department): DepartmentDto = DepartmentDto(
            id = department.id,
            name = department.name
        )
    }
}