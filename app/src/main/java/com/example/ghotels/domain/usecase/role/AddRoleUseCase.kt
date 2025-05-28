package com.example.ghotels.domain.usecase.role

import com.example.ghotels.data.model.RoleDto
import com.example.ghotels.data.repository.RoleRepository
import com.example.ghotels.domain.model.Role

class AddRoleUseCase(
    private val repository: RoleRepository
) {
    suspend operator fun invoke(role: Role): Boolean {
        val dto = RoleDto.from(role)
        return repository.add(dto)
    }
}

