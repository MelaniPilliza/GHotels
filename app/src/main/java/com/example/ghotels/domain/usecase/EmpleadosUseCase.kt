package com.example.ghotels.domain.usecase

import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.repository.EmpleadoRepository

class EmpleadosUseCase(private val repository: EmpleadoRepository) {
    suspend operator fun invoke(): Result<List<UserDto>> {
        return repository.obtenerEmpleados()
    }
}