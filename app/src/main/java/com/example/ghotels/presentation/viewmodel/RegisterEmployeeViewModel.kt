package com.example.ghotels.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ghotels.domain.usecase.employee.RegisterEmployeeUseCase
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.domain.model.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterEmployeeViewModel(
    private val registerEmployeeUseCase: RegisterEmployeeUseCase
) : ViewModel() {

    private val _registrationSuccess = MutableStateFlow<Boolean?>(null)
    val registrationSuccess: StateFlow<Boolean?> = _registrationSuccess

    fun registerEmployee(employee: Employee) {
        viewModelScope.launch {
            val result = registerEmployeeUseCase(employee)
            if (result.isSuccess) {
                Log.d("REGISTER_EMPLOYEE", "Empleado registrado con éxito")
                _registrationSuccess.value = true
            } else {
                Log.e("REGISTER_EMPLOYEE", "Error al registrar empleado", result.exceptionOrNull())
                _registrationSuccess.value = false
            }
        }
    }
}
