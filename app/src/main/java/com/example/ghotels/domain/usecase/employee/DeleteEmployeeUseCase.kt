package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.repository.EmployeeRepository

class DeleteEmployeeUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(id: Long): Result<Unit> {
        return try {
            val response = repository.delete(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al eliminar empleado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

