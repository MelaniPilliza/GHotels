package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.PermissionTypeDto
import com.example.ghotels.domain.model.PermissionType
import com.example.ghotels.domain.usecase.permissiontype.AddPermissionTypeUseCase
import com.example.ghotels.domain.usecase.permissiontype.DeletePermissionTypeUseCase
import com.example.ghotels.domain.usecase.permissiontype.ListPermissionTypeUseCase
import com.example.ghotels.domain.usecase.permissiontype.UpdatePermissionTypeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PermissionTypeViewModel(
    private val listPermissionTypeUseCase: ListPermissionTypeUseCase,
    private val addPermissionTypeUseCase: AddPermissionTypeUseCase,
    private val updatePermissionTypeUseCase: UpdatePermissionTypeUseCase,
    private val deletePermissionTypeUseCase: DeletePermissionTypeUseCase
) : ViewModel() {

    private val _permissionTypes = MutableStateFlow<List<PermissionType>>(emptyList())
    val permissionTypes: StateFlow<List<PermissionType>> = _permissionTypes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadPermissionTypes()
    }

    fun loadPermissionTypes() {
        viewModelScope.launch {
            val result = listPermissionTypeUseCase()
            result.onSuccess {
                _permissionTypes.value = it
            }.onFailure {
                _error.value = it.message ?: "Error al cargar permiso"
            }
        }
    }

    fun addPermissionType(
        name: String,
        description: String,
        requiresApproval: Boolean,
        unlimited: Boolean,
        availableDays: Int?
    ) {
        viewModelScope.launch {
            val newPermission = PermissionType(
                name = name,
                description = description,
                requiresApproval = requiresApproval,
                unlimited = unlimited,
                annualAvailableDays = availableDays
            )
            val result = addPermissionTypeUseCase(newPermission)
            result.onSuccess {
                loadPermissionTypes()
            }.onFailure {
                _error.value = "❌ Error al añadir tipo de permiso"
            }
        }
    }

    fun updatePermissionType(
        id: Long,
        name: String,
        description: String,
        requiresApproval: Boolean,
        unlimited: Boolean,
        availableDays: Int?
    ) {
        viewModelScope.launch {
            val updated = PermissionType(
                id = id,
                name = name,
                description = description,
                requiresApproval = requiresApproval,
                unlimited = unlimited,
                annualAvailableDays = availableDays
            )
            val result = updatePermissionTypeUseCase(id, updated)
            result.onSuccess {
                loadPermissionTypes()
            }.onFailure {
                _error.value = "❌ Error al actualizar tipo de permiso"
            }
        }
    }

    fun deletePermissionType(id: Long) {
        viewModelScope.launch {
            val result = deletePermissionTypeUseCase(id)
            result.onSuccess {
                loadPermissionTypes()
            }.onFailure {
                _error.value = "❌ Error al eliminar tipo de permiso"
            }
        }
    }
}
