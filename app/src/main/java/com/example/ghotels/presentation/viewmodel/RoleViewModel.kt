package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.RoleDto
import com.example.ghotels.domain.model.Role
import com.example.ghotels.domain.usecase.role.AddRoleUseCase
import com.example.ghotels.domain.usecase.role.DeleteRoleUseCase
import com.example.ghotels.domain.usecase.role.ListRoleUseCase
import com.example.ghotels.domain.usecase.role.UpdateRoleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoleViewModel(
    private val listUseCase: ListRoleUseCase,
    private val addUseCase: AddRoleUseCase,
    private val updateUseCase: UpdateRoleUseCase,
    private val deleteUseCase: DeleteRoleUseCase
) : ViewModel() {


    private val _roles = MutableStateFlow<List<Role>>(emptyList())
    val roles: StateFlow<List<Role>> = _roles
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadRoles()
    }


    fun loadRoles() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _roles.value = listUseCase()
            } catch (e: Exception) {
                _error.value = "Error al cargar los roles"
            } finally {
                _loading.value = false
            }
        }
    }


    fun addRole(name: String, description: String) {
        viewModelScope.launch {
            try {
                val newRole = Role(name = name, description = description)
                val success = addUseCase(newRole)
                if (success) loadRoles()
                else _error.value = "Error al crear el rol"
            } catch (e: Exception) {
                _error.value = "Error inesperado al crear el rol"
            }
        }
    }


    fun updateRole(id: Long?, name: String, description: String) {
        viewModelScope.launch {
            try {
                val updated = Role(id = id, name = name, description = description)
                val success = updateUseCase(updated)
                if (success) loadRoles()
                else _error.value = "Error al actualizar el rol"
            } catch (e: Exception) {
                _error.value = "Error inesperado al actualizar el rol"
            }
        }
    }


    fun deleteRole(id: Long?) {
        viewModelScope.launch {
            try {
                val success = deleteUseCase(id)
                if (success) loadRoles()
                else _error.value = "Error al eliminar el rol"
            } catch (e: Exception) {
                _error.value = "Error inesperado al eliminar el rol"
            }
        }
    }
}
