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

    private val _loadingRecovery = MutableStateFlow(false)
    val loadingRecovery: StateFlow<Boolean> = _loadingRecovery

    private val _successRecovery = MutableStateFlow<Boolean?>(null)
    val successRecovery: StateFlow<Boolean?> = _successRecovery

    private val _errorRecovery = MutableStateFlow<String?>(null)
    val errorRecovery: StateFlow<String?> = _errorRecovery

    private val _loadingReset = MutableStateFlow(false)
    val loadingReset: StateFlow<Boolean> = _loadingReset

    private val _successReset = MutableStateFlow<Boolean?>(null)
    val successReset: StateFlow<Boolean?> = _successReset

    private val _errorReset = MutableStateFlow<String?>(null)
    val errorReset: StateFlow<String?> = _errorReset

    fun recover(email: String) {
        viewModelScope.launch {
            _loadingRecovery.value = true
            _errorRecovery.value = null
            try {
                val success = recoverUseCase(email)
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

    fun reset(token: String, newPassword: String) {
        viewModelScope.launch {
            _loadingReset.value = true
            _errorReset.value = null
            try {
                val success = resetUseCase(token, newPassword)
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






