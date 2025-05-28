package com.example.ghotels.domain.usecase.role

import com.example.ghotels.data.model.RoleDto
import com.example.ghotels.data.repository.RoleRepository
import com.example.ghotels.domain.model.Role

class UpdateRoleUseCase(
    private val repository: RoleRepository
) {
    suspend operator fun invoke(role: Role): Boolean {
        val id = role.id ?: return false
        val dto = RoleDto.from(role)
        return repository.update(id, dto)
    }
}
