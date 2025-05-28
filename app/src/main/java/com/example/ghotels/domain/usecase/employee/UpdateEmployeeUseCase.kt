package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.data.repository.EmployeeRepository
import com.example.ghotels.domain.model.Employee

class UpdateEmployeeUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(id: Long, employee: Employee): Result<Unit> {
        val dto = RegisterEmployeeDto.fromEmployee(employee) // Conversión en el caso de uso
        return try {
            val response = repository.update(id, dto)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Failed to update employee"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
