package com.example.ghotels.domain.usecase.department

import com.example.ghotels.data.model.DepartmentDto
import com.example.ghotels.data.repository.DepartmentRepository
import com.example.ghotels.domain.model.Department

class ListDepartmentUseCase(
    private val repository: DepartmentRepository
) {
    suspend operator fun invoke(): List<Department> {
        return repository.getAll().map { it.toDepartment() }
    }
}