package com.example.ghotels.domain.usecase

import com.example.ghotels.data.model.RegistroEmpleadoDto
import com.example.ghotels.data.repository.EmpleadoRepository

class RegistrarEmpleadoUseCase(
    private val empleadoRepository: EmpleadoRepository
) {
    suspend operator fun invoke(dto: RegistroEmpleadoDto): Result<Unit> {
        return empleadoRepository.registrarEmpleado(dto)
    }
}
