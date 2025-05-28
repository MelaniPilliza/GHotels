package com.example.ghotels.presentation.viewmodel.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.domain.usecase.employee.LoginUseCase
import com.example.ghotels.domain.usecase.employee.SaveUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    // Campos observables para el formulario
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // Resultado del login (usuario logueado)
    private val _loginSuccess = MutableStateFlow<UserDto?>(null)
    val loginSuccess: StateFlow<UserDto?> = _loginSuccess

    // Posibles errores del login
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    /**
     * Actualiza el campo del correo
     */
    fun updateMail(value: String) {
        _email.value = value
    }

    /**
     * Actualiza el campo de la contraseña
     */
    fun updatePassword(value: String) {
        _password.value = value
    }

    /**
     * Realiza el proceso de login con el backend
     */
    fun doLogin() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Por favor, completa ambos campos"
            _loginSuccess.value = null
            return
        }

        viewModelScope.launch {
            val loginDto = LoginDto(
                mail = _email.value,
                password = _password.value
            )

            val result = loginUseCase(loginDto)

            result.onSuccess {
                // Guardar usuario en memoria y persistencia
                saveUserUseCase.setUser(it)
                _loginSuccess.value = it
                _errorMessage.value = null
            }.onFailure {
                Log.e("LoginViewModel", "Login fallido", it)
                _errorMessage.value = it.message
                _loginSuccess.value = null
            }
        }
    }

    /**
     * Verifica si ya hay sesión iniciada al abrir la app.
     * Si hay un usuario guardado, se salta el login automáticamente.
     */
    fun checkExistingSession() {
        viewModelScope.launch {
            saveUserUseCase.loadUser()
            val user = saveUserUseCase.user.value
            if (user != null) {
                _loginSuccess.value = user // ← Activará redirección
            }
        }
    }
}
