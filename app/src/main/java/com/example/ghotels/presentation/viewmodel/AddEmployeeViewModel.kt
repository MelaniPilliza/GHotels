package com.example.ghotels.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ghotels.domain.usecase.RegistrarEmpleadoUseCase
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.RegistroEmpleadoDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddEmployeeViewModel(
    private val registrarEmpleadoUseCase: RegistrarEmpleadoUseCase
) : ViewModel() {

    fun registrarEmpleado(dto: RegistroEmpleadoDto) {
        viewModelScope.launch {
            val resultado = registrarEmpleadoUseCase(dto)
            if (resultado.isSuccess) {
                Log.d("ADD_EMPLOYEE", "✅ Empleado creado con éxito")
                // Opcional: navega atrás o muestra mensaje
            } else {
                Log.e("ADD_EMPLOYEE", "❌ Error al crear empleado", resultado.exceptionOrNull())
            }
        }
    }
}
