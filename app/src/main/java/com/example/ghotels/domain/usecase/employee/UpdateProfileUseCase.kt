package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.repository.EmployeeRepository

class UpdateProfileUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(id: Long, userDto: UserDto): Result<Unit> {
        return try {
            repository.updateProfile(id, userDto)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
