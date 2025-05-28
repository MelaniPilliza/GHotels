package com.example.ghotels.domain.usecase.role

import com.example.ghotels.data.model.RoleDto
import com.example.ghotels.data.repository.RoleRepository
import com.example.ghotels.domain.model.Role

class ListRoleUseCase(
    private val repository: RoleRepository
) {
    suspend operator fun invoke(): List<Role> {
        return repository.getAll().map { it.toRole() }
    }
}

