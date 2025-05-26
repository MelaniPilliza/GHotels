package com.example.ghotels.domain.usecase

import com.example.ghotels.data.model.UserDto
import com.example.ghotels.data.repository.EmpleadoRepository

class UpdateProfileUseCase(
    private val empleadoRepository: EmpleadoRepository
) {
    suspend operator fun invoke(id: Long, dto: UserDto): Result<Unit> =
        empleadoRepository.actualizarPerfil(id, dto)
}
