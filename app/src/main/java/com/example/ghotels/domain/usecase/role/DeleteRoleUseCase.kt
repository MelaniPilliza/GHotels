package com.example.ghotels.domain.usecase.role

import com.example.ghotels.data.repository.RoleRepository

class DeleteRoleUseCase(
    private val repository: RoleRepository
) {
    suspend operator fun invoke(id: Long?): Boolean {
        return id?.let { repository.delete(it) } ?: false
    }
}
