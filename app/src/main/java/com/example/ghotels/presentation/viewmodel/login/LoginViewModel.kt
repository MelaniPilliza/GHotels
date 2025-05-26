package com.example.ghotels.presentation.viewmodel.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.data.model.LoginDto
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.domain.model.Empleado
import com.example.ghotels.domain.usecase.LoginUseCase
import com.example.ghotels.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginSuccess = MutableStateFlow<UserDto?>(null)
    val loginSuccess: StateFlow<UserDto?> = _loginSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun updateMail(value: String) {
        _email.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun doLogin() {
        viewModelScope.launch {
            val loginDto = LoginDto(
                mail = _email.value,
                password = _password.value
            )

            val result = loginUseCase(loginDto)

            result.onSuccess {
                saveUserUseCase.setUsuario(it) // ✅ Guardamos el usuario globalmente
                _loginSuccess.value = it
                _errorMessage.value = null
            }.onFailure {
                Log.e("LoginViewModel", "Login fallido", it)
                _errorMessage.value = it.message
                _loginSuccess.value = null
            }
        }
    }
}
