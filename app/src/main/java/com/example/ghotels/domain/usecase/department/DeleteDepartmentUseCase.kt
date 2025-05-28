package com.example.ghotels.domain.usecase.department

import com.example.ghotels.data.repository.DepartmentRepository

class DeleteDepartmentUseCase(
    private val repository: DepartmentRepository
) {
    suspend operator fun invoke(id: Long?): Boolean {
        return id?.let { repository.delete(it) } ?: false
    }
}