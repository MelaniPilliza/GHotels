package com.example.ghotels.domain.usecase.permissiontype

import com.example.ghotels.data.repository.PermissionTypeRepository

class DeletePermissionTypeUseCase(private val repository: PermissionTypeRepository) {
    suspend operator fun invoke(id: Long): Result<Unit> = runCatching {
        val response = repository.delete(id)
        if (!response.isSuccessful) throw Exception("Failed to delete permission type")
    }
}
