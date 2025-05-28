package com.example.ghotels.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.domain.usecase.employee.ListEmployeeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StaffViewModel(
    private val listEmployeeUseCase: ListEmployeeUseCase
) : ViewModel() {

    private val _employees = MutableStateFlow<List<UserDto>>(emptyList())
    val employees: StateFlow<List<UserDto>> = _employees

    init {
        loadEmployees()
    }

    private fun loadEmployees() {
        viewModelScope.launch {
            val result = listEmployeeUseCase()
            result
                .onSuccess {
                    Log.d("STAFF_VIEWMODEL", "✅ Empleados cargados: ${it.size}")
                    _employees.value = it
                }
                .onFailure {
                    Log.e("STAFF_VIEWMODEL", "❌ Error al cargar empleados: ${it.message}")
                }
        }
    }
}

