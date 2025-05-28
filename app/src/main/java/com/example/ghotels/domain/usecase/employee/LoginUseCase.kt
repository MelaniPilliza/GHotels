package com.example.ghotels.domain.usecase.employee

import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.repository.EmployeeRepository

class LoginUseCase(private val repository: EmployeeRepository) {

    suspend operator fun invoke(loginDto: LoginDto): Result<UserDto> {
        return try {
            val loginResponse = repository.login(loginDto)
            if (loginResponse != null) {
                Result.success(loginResponse)
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


