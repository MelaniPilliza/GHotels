package com.example.ghotels.domain.usecase.permissionrequest

import com.example.ghotels.data.model.PermissionBalanceDto
import com.example.ghotels.data.repository.PermissionRequestRepository

class GetPermissionBalancesUseCase(
    private val repository: PermissionRequestRepository
) {
    suspend operator fun invoke(employeeId: Long): Result<List<PermissionBalanceDto>> {
        return repository.getBalances(employeeId)
    }
}
