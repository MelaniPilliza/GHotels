package com.example.ghotels.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.data.model.UpdateEmployeeDto
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.domain.model.Employee
import com.example.ghotels.domain.usecase.employee.DeleteEmployeeUseCase
import com.example.ghotels.domain.usecase.employee.GetEmployeeByIdUseCase
import com.example.ghotels.domain.usecase.employee.ListEmployeeUseCase
import com.example.ghotels.domain.usecase.employee.UpdateEmployeeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class StaffViewModel(
    private val listEmployeeUseCase: ListEmployeeUseCase,
    private val deleteEmployeeUseCase: DeleteEmployeeUseCase,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val updateEmployeeUseCase: UpdateEmployeeUseCase
) : ViewModel() {

    private val _employees = MutableStateFlow<List<UserDto>>(emptyList())
    val employees: StateFlow<List<UserDto>> = _employees

    private val _selectedEmployee = MutableStateFlow<UserDto?>(null)
    val selectedEmployee: StateFlow<UserDto?> = _selectedEmployee

    private val _updateSuccess = MutableStateFlow<Boolean?>(null)
    val updateSuccess: StateFlow<Boolean?> = _updateSuccess

    init {
        loadEmployees()
    }

    fun loadEmployees() {
        viewModelScope.launch {
            listEmployeeUseCase()
                .onSuccess {
                    _employees.value = it
                }
                .onFailure {
                    Log.e("STAFF_VIEWMODEL", "Error al cargar empleados: ${it.message}")
                }
        }
    }

    fun loadEmployeeById(id: Long) {
        viewModelScope.launch {
            getEmployeeByIdUseCase(id)
                .onSuccess {
                    _selectedEmployee.value = it
                }
                .onFailure {
                    Log.e("STAFF_VIEWMODEL", "Error al cargar empleado por ID: ${it.message}", it)
                    _selectedEmployee.value = null
                }
        }
    }

    fun deleteEmployee(id: Long) {
        viewModelScope.launch {
            val result = deleteEmployeeUseCase(id)
            if (result.isSuccess) {
                _employees.value = _employees.value.filterNot { it.id == id }
            } else {
                Log.e("STAFF_VIEWMODEL", "Error al eliminar empleado: ${result.exceptionOrNull()}")
            }
        }
    }

    fun updateEmployee(id: Long, dto: UpdateEmployeeDto) {
        viewModelScope.launch {
            try {
                val response: Response<UserDto> = updateEmployeeUseCase(id, dto)
                if (response.isSuccessful) {
                    _updateSuccess.value = true
                    loadEmployees()
                } else {
                    Log.e("STAFF_VIEWMODEL", "Error en updateEmployee: Código ${response.code()}")
                    _updateSuccess.value = false
                }
            } catch (e: Exception) {
                Log.e("STAFF_VIEWMODEL", "Excepción en updateEmployee: ${e.message}", e)
                _updateSuccess.value = false
            }
        }
    }

    fun findEmployeeById(id: Long): UserDto? {
        return _employees.value.find { it.id == id }
    }
}