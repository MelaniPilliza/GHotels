package com.example.ghotels.domain.usecase.permissiontype

import com.example.ghotels.data.repository.PermissionTypeRepository
import com.example.ghotels.domain.model.PermissionType


class ListPermissionTypeUseCase(private val repository: PermissionTypeRepository) {
    suspend operator fun invoke(): Result<List<PermissionType>> = runCatching {
        repository.getAll().map { it.toPermissionType() }
    }
}

