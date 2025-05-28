package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.domain.usecase.employee.SaveUserUseCase
import kotlinx.coroutines.flow.StateFlow


class HomeViewModel(
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {
    val user: StateFlow<UserDto?> = saveUserUseCase.user

    fun getRole(): String? = user.value?.role

    fun getFullName(): String =
        listOfNotNull(user.value?.firstName, user.value?.lastName).joinToString(" ")

    fun getRoleLabel(): String = when (user.value?.role) {
        "ADMIN" -> "Administrador"
        "RRHH" -> "Recursos Humanos"
        else -> user.value?.role ?: "Empleado"
    }
}