package com.example.ghotels.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.domain.model.Empleado
import com.example.ghotels.domain.usecase.LoginUseCase
import com.example.ghotels.data.model.LoginRespuestaDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginSuccess = MutableStateFlow<LoginRespuestaDto?>(null)
    val loginSuccess: StateFlow<LoginRespuestaDto?> = _loginSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun doLogin() {
        viewModelScope.launch {
            val loginDto = LoginDto(
                correo = _email.value,
                password = _password.value
            )

            val result = loginUseCase(loginDto)

            result.onSuccess {
                _loginSuccess.value = it
                _errorMessage.value = null
            }.onFailure {
                _errorMessage.value = it.message
                _loginSuccess.value = null
            }
        }
    }
}