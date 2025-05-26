package com.example.ghotels.domain.usecase



import com.example.ghotels.data.model.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SaveUserUseCase {
    private val _usuario = MutableStateFlow<UserDto?>(null)
    val usuario: StateFlow<UserDto?> = _usuario

    fun setUsuario(usuario: UserDto) {
        _usuario.value = usuario
    }

    fun clearUsuario() {
        _usuario.value = null
    }
}

