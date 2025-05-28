package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.DepartmentDto
import com.example.ghotels.domain.model.Department
import com.example.ghotels.domain.usecase.department.AddDepartmentUseCase
import com.example.ghotels.domain.usecase.department.DeleteDepartmentUseCase
import com.example.ghotels.domain.usecase.department.ListDepartmentUseCase
import com.example.ghotels.domain.usecase.department.UpdateDepartmentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DepartmentViewModel(
    private val listUseCase: ListDepartmentUseCase,
    private val addUseCase: AddDepartmentUseCase,
    private val updateUseCase: UpdateDepartmentUseCase,
    private val deleteUseCase: DeleteDepartmentUseCase
) : ViewModel() {

    private val _departments = MutableStateFlow<List<Department>>(emptyList())
    val departments: StateFlow<List<Department>> = _departments

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadDepartments()
    }

    fun loadDepartments() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _departments.value = listUseCase()
            } catch (e: Exception) {
                _error.value = "❌ Error al cargar departamentos"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addDepartment(name: String) {
        viewModelScope.launch {
            try {
                val newDepartment = Department(name = name)
                val success = addUseCase(newDepartment)
                if (success) loadDepartments()
                else _error.value = "❌ Error al crear el departamento"
            } catch (e: Exception) {
                _error.value = "❌ Error inesperado al añadir departamento"
            }
        }
    }

    fun updateDepartment(id: Long?, name: String) {
        viewModelScope.launch {
            try {
                val department = Department(id = id, name = name)
                val success = updateUseCase(department)
                if (success) loadDepartments()
                else _error.value = "❌ Error al actualizar el departamento"
            } catch (e: Exception) {
                _error.value = "❌ Error inesperado al actualizar departamento"
            }
        }
    }

    fun deleteDepartment(id: Long?) {
        viewModelScope.launch {
            try {
                val success = deleteUseCase(id)
                if (success) loadDepartments()
                else _error.value = "❌ Error al eliminar el departamento"
            } catch (e: Exception) {
                _error.value = "❌ Error inesperado al eliminar departamento"
            }
        }
    }
}