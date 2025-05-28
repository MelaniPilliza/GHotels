package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.data.repository.EmployeeRepository
import com.example.ghotels.domain.model.Employee

class UpdateEmployeeUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(id: Long, employee: Employee): Result<Unit> {
        val dto = RegisterEmployeeDto.fromEmployee(employee) // Conversion
        return try {
            val response = repository.update(id, dto)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error al actualizar empleado"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
