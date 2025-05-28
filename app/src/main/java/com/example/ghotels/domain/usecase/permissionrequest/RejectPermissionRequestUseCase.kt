package com.example.ghotels.domain.usecase.permissionrequest

import com.example.ghotels.data.repository.PermissionRequestRepository

class RejectPermissionRequestUseCase(
    private val repository: PermissionRequestRepository
) {
    suspend operator fun invoke(id: Long): Boolean {
        return repository.reject(id)
    }
}
