package com.example.ghotels.domain.usecase

import android.util.Log
import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.LoginRespuestaDto
import com.example.ghotels.data.repository.EmpleadoRepository
import com.example.ghotels.domain.model.Empleado

class LoginUseCase(private val repository: EmpleadoRepository) {

    suspend operator fun invoke(loginDto: LoginDto): Result<LoginRespuestaDto> {
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


