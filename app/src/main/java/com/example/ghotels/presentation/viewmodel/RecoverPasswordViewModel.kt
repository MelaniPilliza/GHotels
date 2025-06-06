package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghotels.domain.usecase.password.RecoverPasswordUseCase
import com.example.ghotels.domain.usecase.password.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecoverPasswordViewModel(
    private val recoverUseCase: RecoverPasswordUseCase,
    private val resetUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword

    //LOGICA DE RECUPERACION
    // Estado de carga durante el envío del correo con el token de recuperación
    private val _loadingRecovery = MutableStateFlow(false)
    val loadingRecovery: StateFlow<Boolean> = _loadingRecovery

    // Indica si el envío del correo de recuperación fue exitoso o error
    private val _successRecovery = MutableStateFlow<Boolean?>(null)
    val successRecovery: StateFlow<Boolean?> = _successRecovery
    private val _errorRecovery = MutableStateFlow<String?>(null)
    val errorRecovery: StateFlow<String?> = _errorRecovery

    //LOGCA DE RESTABLECIMIENTO
    // Estado de carga durante el restablecimiento de contraseña con el token
    private val _loadingReset = MutableStateFlow(false)
    val loadingReset: StateFlow<Boolean> = _loadingReset

    // Indica si el restablecimiento de contraseña fue exitoso o error
    private val _successReset = MutableStateFlow<Boolean?>(null)
    val successReset: StateFlow<Boolean?> = _successReset
    private val _errorReset = MutableStateFlow<String?>(null)
    val errorReset: StateFlow<String?> = _errorReset


    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updateToken(newToken: String) {
        _token.value = newToken
    }

    fun updateNewPassword(newPwd: String) {
        _newPassword.value = newPwd
    }



    //RECUPERACION DE CONTRASEÑA
    fun recover() {
        val currentEmail = _email.value.trim()
        viewModelScope.launch {
            _loadingRecovery.value = true
            _errorRecovery.value = null
            try {
                val success = recoverUseCase(currentEmail)
                if (success) {
                    _successRecovery.value = true
                } else {
                    _errorRecovery.value = "Introduzca un correo válido"
                    _successRecovery.value = false
                }
            } catch (e: Exception) {
                _errorRecovery.value = "Error inesperado"
                _successRecovery.value = false
            } finally {
                _loadingRecovery.value = false
            }
        }
    }

    //RESTABLECER CONTRASEÑA
    fun reset() {
        val currentToken = _token.value.trim()
        val currentNewPassword = _newPassword.value.trim()
        viewModelScope.launch {
            _loadingReset.value = true
            _errorReset.value = null
            try {
                val success = resetUseCase(currentToken, currentNewPassword)
                if (success) {
                    _successReset.value = true
                } else {
                    _errorReset.value = "Token inválido o expirado"
                    _successReset.value = false
                }
            } catch (e: Exception) {
                _errorReset.value = "Error inesperado"
                _successReset.value = false
            } finally {
                _loadingReset.value = false
            }
        }
    }
}






