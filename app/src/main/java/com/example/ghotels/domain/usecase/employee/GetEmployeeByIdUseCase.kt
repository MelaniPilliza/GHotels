package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.repository.EmployeeRepository

class GetEmployeeByIdUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(id: Long): Result<UserDto> {
        return repository.getById(id)
    }
}

