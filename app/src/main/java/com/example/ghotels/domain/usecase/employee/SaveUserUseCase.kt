package com.example.ghotels.domain.usecase.employee



import com.example.ghotels.data.local.UserDataStore
import com.example.ghotels.data.model.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//Mi repository esta en local
class SaveUserUseCase(private val userDataStore: UserDataStore) {
    private val _user = MutableStateFlow<UserDto?>(null)
    val user: StateFlow<UserDto?> = _user

    suspend fun setUser(user: UserDto) {
        _user.value = user
        userDataStore.saveUser(user) // 🔒 Guardar en persistencia
    }

    suspend fun loadUser() {
        _user.value = userDataStore.getUser() // ✅ Cargar al abrir app
    }

    suspend fun clearUser() {
        _user.value = null
        userDataStore.clear()
    }
}



