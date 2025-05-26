package com.example.ghotels.presentation.viewmodel

import android.util.Log
import com.example.ghotels.domain.usecase.EmpleadosUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StaffViewModel(
    private val empleadosUseCase: EmpleadosUseCase
) : ViewModel() {

    private val _empleados = MutableStateFlow<List<UserDto>>(emptyList())
    val empleados: StateFlow<List<UserDto>> = _empleados

    init {
        cargarEmpleados()
    }

    private fun cargarEmpleados() {
        viewModelScope.launch {
            val resultado = empleadosUseCase()
            resultado
                .onSuccess {
                    Log.d("STAFF_VIEWMODEL", "✅ Empleados cargados: ${it.size}")
                    _empleados.value = it
                }
                .onFailure {
                    Log.e("STAFF_VIEWMODEL", "❌ Error al cargar empleados: ${it.message}")
                }
        }
    }

}
