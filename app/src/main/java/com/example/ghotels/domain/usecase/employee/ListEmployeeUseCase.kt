package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.repository.EmployeeRepository

class ListEmployeeUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(): Result<List<UserDto>> = runCatching {
        repository.getAll()
    }
}
