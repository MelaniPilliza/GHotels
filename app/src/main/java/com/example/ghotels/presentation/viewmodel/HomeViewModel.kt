package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.flow.StateFlow


class HomeViewModel(
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {
    val usuario: StateFlow<UserDto?> = saveUserUseCase.usuario

    fun getRol(): String? = usuario.value?.rol
    fun getNombre(): String = usuario.value?.nombre ?: ""
    fun getRolLabel(): String = when (usuario.value?.rol) {
        "ADMIN" -> "Administrador"
        "RRHH" -> "Recursos Humanos"
        else -> usuario.value?.rol ?: "Empleado"
    }
}