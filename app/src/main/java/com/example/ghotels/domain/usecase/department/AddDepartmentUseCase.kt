package com.example.ghotels.domain.usecase.department

import com.example.ghotels.data.model.DepartmentDto
import com.example.ghotels.data.repository.DepartmentRepository
import com.example.ghotels.domain.model.Department

class AddDepartmentUseCase(
    private val repository: DepartmentRepository
) {
    suspend operator fun invoke(department: Department): Boolean {
        val dto = DepartmentDto.from(department)
        return repository.add(dto)
    }
}

