package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.data.repository.EmployeeRepository
import com.example.ghotels.domain.model.Employee

class RegisterEmployeeUseCase(private val repository: EmployeeRepository) {
    suspend operator fun invoke(employee: Employee): Result<Unit> {
        val dto = RegisterEmployeeDto.fromEmployee(employee)
        return try {
            val response = repository.register(dto)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error en el registro de empleado"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

